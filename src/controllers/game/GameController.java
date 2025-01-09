package controllers.game;
import controllers.AppController;
import controllers.map.MapController;
import enums.DifficultyLevel;
import game.GameSettings;
import models.map.Country;
import models.game.GameModel;
import views.game.GameView;

import javax.swing.*;

public class GameController implements Runnable{
    //MODELS
    private final GameModel gameModel;
    //CONTROLLERS
    private  final MapController mapController;
    //VIEWS
    private  final GameView gameView;
    //LOOP
    private boolean isRunning;
    private Thread gameThread;

    public GameController(AppController appController) {
        //MODELS
        this.gameModel = new GameModel();
        //CONTROLLERS
        this.mapController = new MapController(gameModel,this);
        this.gameView = new GameView(this, gameModel,mapController);

    }

    public GameView getGameView() {
        return gameView;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                gameModel.updateModel();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void startNewGame() {
        resetGame();
        startGameLoop();
        gameModel.startNewGame();
        gameView.showGameStartAlert();
    }

    public void resetGame() {
        gameModel.initializeGameData();
    }

    private void startGameLoop() {
        if (gameThread == null || !gameThread.isAlive()) {
            isRunning = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

}
