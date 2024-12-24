package controllers;

import enums.GameState;
import models.AppModel;
import views.*;
import views.GameFrame;
import javax.swing.*;
import java.awt.*;

public class AppController extends JPanel implements Runnable{
    private boolean isRunning = false;

    //MODELS
    private final AppModel appModel;
    // CONTROLLERS
    public PauseController pauseController;
    public GameController gameController;
    // GAME SCREENS
    public StartGameView startGameView;
    //VIEWS
    public GameFrame gameFrame;


    public AppController(){
        //MODELS
        appModel = new AppModel();
        // CONTROLLERS
        pauseController = new PauseController(this);
        gameController = new GameController(this);
        // VIEWS
        gameFrame = new GameFrame();
        startGameView = new StartGameView(this);

        gameFrame.add(startGameView,"START");
        gameFrame.add(gameController.getGameView(),"GAME");
        gameFrame.add(pauseController.getPauseView(),"PAUSE");

        setGameState(GameState.NOT_STARTED);

        gameFrame.setVisible(true);
    }

    public void setGameState(GameState gameState) {
        appModel.setGameState(gameState);

        CardLayout layout = (CardLayout) gameFrame.getContentPane().getLayout();

        switch (gameState) {
            case NOT_STARTED -> layout.show(gameFrame.getContentPane(), "START");
            case PLAYING -> layout.show(gameFrame.getContentPane(), "GAME");
            case PAUSED -> layout.show(gameFrame.getContentPane(), "PAUSE");
        }
    }



    @Override
    public void run() {
        isRunning = true;
        long lastTime = System.nanoTime();
        long currentTime;
        double delta = 0;
        double updateInterval = 1_000_000_000.0;

        while (isRunning) {
            if (appModel.getGameState() == GameState.PAUSED) {
                continue;
            }

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / updateInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                gameController.updateGameDate();
                delta--;
            }
        }
    }

    public void pauseGame() {
        isRunning = false;
    }

    public void resumeGame() {
        if (!isRunning) {
            isRunning = true;
            new Thread(this).start();
        }
    }

}
