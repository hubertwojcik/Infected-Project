package controllers.game;
import controllers.AppController;
import controllers.map.MapController;
import enums.TransportType;
import models.country.Country;
import models.game.GameModel;
import models.game.GameObserver;
import views.game.GameView;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameController implements Runnable, KeyListener {
    //MODELS
    private final GameModel gameModel;
    //CONTROLLERS
    private final AppController appController;
    private  final MapController mapController;
    //VIEWS
    private  final GameView gameView;
    //LOOP
    private boolean isRunning;
    private Thread gameThread;

    public GameController(AppController appController) {
        this.appController = appController;
        //MODELS
        this.gameModel = new GameModel();
        //CONTROLLERS
        this.mapController = new MapController(gameModel,this);
        this.gameView = new GameView(this, gameModel,mapController);



        gameView.addKeyListener(this);
        gameView.setFocusable(true);
        gameView.requestFocusInWindow();

        this.gameModel.addObserver(new GameObserver() {
            @Override
            public void onDayUpdate(int dayCounter) {
            }

            @Override
            public void onGlobalStatsUpdate(int infected, int cured, int dead) {
            }

            @Override
            public void onSelectedCountryUpdate(String countryName, double countryPoints, int population, int suspectible, int infected, int cured, int dead, double infectedRate, double recoveryRestinatce, double moratyliRate) {
            }

            @Override
            public void onGameEnd() {
                endGame();
            }

            @Override
            public void onTransportStateUpdate(Country country, TransportType transportType, boolean isEnabled) {
            }
        });
    }

    public GameView getGameView() {
        return gameView;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                gameModel.updateModel();
                SwingUtilities.invokeLater(gameView::repaint);
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

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_Q) {
            stopGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    private void stopGame(){
        resetGame();
        gameThread.interrupt();
        appController.showHomeView();
    }

    private void endGame(){
        gameThread.interrupt();
        appController.showGameEndView(gameModel.getDayCounter(), gameModel.getTotalCured(), gameModel.getTotalDead());
    }
}
