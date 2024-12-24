package controllers;

import enums.GameState;
import models.AppModel;
import views.*;
import views.GameFrame;
import javax.swing.*;
import java.awt.*;

public class AppController extends JPanel implements Runnable{
    private boolean isRunning = false;
    private int FPS = 60;
    private double timeElapsed = 0;

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
        appModel.setGameState(gameState); // Aktualizacja modelu

        CardLayout layout = (CardLayout) gameFrame.getContentPane().getLayout();

        switch (gameState) {
            case NOT_STARTED -> layout.show(gameFrame.getContentPane(), "START");
            case PLAYING -> layout.show(gameFrame.getContentPane(), "GAME");
            case PAUSED -> layout.show(gameFrame.getContentPane(), "PAUSE");
        }
    }

    public GameState getGameState() {
        return appModel.getGameState();
    }
    @Override
    public void run() {
        isRunning = true;
        double drawInterval = (double) 1_000_000_000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (isRunning){
            if (appModel.getGameState() == GameState.PAUSED) {
                continue;
            }

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            timeElapsed += (currentTime - lastTime) / 1_000_000_000.0; // W sekundach
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1_000_000_000){
//                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(){
        //Update Day
        if (timeElapsed >= 5) {
            System.out.println("Date change");
            gameController.updateGameDate();
            timeElapsed = 0;
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
