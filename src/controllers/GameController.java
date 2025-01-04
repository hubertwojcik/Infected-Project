package controllers;
import enums.GameState;
import models.Country;
import models.GameModel;
import views.GameView;

public class GameController {
    //MODELS
    private final GameModel gameModel;
    //CONTROLLERS
    private final MapController mapController;
    //VIEWS
    private final GameView gameView;

    public GameController(AppController appController) {
        //MODELS
        this.gameModel = new GameModel(this);
        //CONTROLLERS
        this.mapController = new MapController(gameModel,this);
        this.gameView = new GameView(this, gameModel,mapController);

    }

    public GameView getGameView() {
        return gameView;
    }

    public void startGame() {
        gameModel.setGameState(GameState.PLAYING);
        gameModel.startGame();
    }

    public void pauseGame() {
        gameModel.setGameState(GameState.PAUSED);
        gameModel.pauseGame();
    }

    public void stopGame() {
        gameModel.setGameState(GameState.NOT_STARTED);
        gameModel.stopGame();
    }


    public void updateGameViews(){
        gameView.updateGameViews();
    }

    public void handleCountrySidebarClick() {
        Country selectedCountry = gameModel.getSelectedCountry();
        if (selectedCountry == null) {
            gameView.getGameSidebarView().hideCountryPanel();
        } else {
            gameView.getGameSidebarView().updateCountryPanel();
        }
    }

}
