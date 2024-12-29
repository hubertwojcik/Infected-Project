package controllers;
import enums.GameState;
import models.Country;
import models.GameModel;
import models.Transport;
import views.GameView;

public class GameController {
    //MODELS
    private final GameModel gameModel;
    //CONTROLLERS
    private final AppController appController;
    private final MapController mapController;
//    private final TransportController transportController;

    //VIEWS
    private final GameView gameView;

    public GameController(AppController appController) {
        //MODELS
        this.gameModel = new GameModel();
        //CONTROLLERS
        this.appController = appController;
//        this.transportController = new TransportController();
        this.mapController = new MapController(gameModel,this);
        this.gameView = new GameView(this, gameModel,mapController);

    }



    public void pauseAllTransports(){
        for (Transport transport : gameModel.getTransports()){
                transport.pause();
        }
    }

    public void resumeAllTransports(){
        for (Transport transport : gameModel.getTransports()){
            transport.resume();
        }
    }

    public void enableTransport(Transport transport) {
        transport.enable();
    }

    public void disableTransport(Transport transport) {
        transport.disable();
    }

    public GameView getGameView() {
        return gameView;
    }

    public void pauseGame(){
        appController.pauseGame();
        appController.setGameState(GameState.PAUSED);
        pauseAllTransports();
    }

    public void resumeGame(){
        resumeAllTransports();
    }

    public void updateSidebar() {
        Country selectedCountry = gameModel.getSelectedCountry();
        if (selectedCountry == null) {
            gameView.getGameSidebarView().hideCountryPanel();
        } else {
            gameView.getGameSidebarView().updateCountryPanel();
        }
    }

    public void updateGameDate(){
        gameModel.advanceOneDay();
        gameView.updateDate();
        gameView.getGameSidebarView().updateCountryPanel();

    }



}
