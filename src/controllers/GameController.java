package controllers;
import enums.GameState;
import models.Country;
import models.GameModel;
import views.GameView;
public class GameController {
    //MODELS
    private final GameModel gameModel;
    //CONTROLLERS
    private final AppController appController;
    private final MapController mapController;
    //VIEWS
    private final GameView gameView;


    public GameController(AppController appController) {
        this.appController = appController;
        this.gameModel = new GameModel();
        this.mapController = new MapController(gameModel,this);
        this.gameView = new GameView(this, gameModel,mapController);
    }

    public GameView getGameView() {
        return gameView;
    }

    public void pauseGame(){
        appController.pauseGame();
        appController.setGameState(GameState.PAUSED);
    }

    public void updateSidebar() {
        gameView.getGameSidebarView().updateCountryPanel();
    }

    public void updateGameDate(){
        gameModel.advanceOneDay();
        gameView.updateDate();
    }

}
