package controllers;

import enums.GameState;
import models.AppModel;
import views.*;
import views.GameFrame;
import javax.swing.*;
import java.awt.*;

public class AppController extends JPanel implements Runnable{
    private final AppModel appModel;
    private boolean isRunning = false;

    public int FPS = 60;

    // GAME SCREENS
    public GameView gameView;
    public StartGameView startGameView;
    public PauseView pauseView;

    public GameState gameState = GameState.NOT_STARTED;
    public GameFrame gameFrame;


    public AppController(){
        appModel = new AppModel();
        gameFrame = new GameFrame();

        startGameView = new StartGameView(this);
        gameView = new GameView(appModel,this);
        pauseView = new PauseView(this);

        gameFrame.add(startGameView,"START");
        gameFrame.add(gameView,"GAME");
        gameFrame.add(pauseView,"PAUSE");

        setGameState(GameState.NOT_STARTED);

        gameFrame.setVisible(true);
    }

    public void setGameState(GameState gameState){
        this.gameState = gameState;

        CardLayout layout = (CardLayout) gameFrame.getContentPane().getLayout();

        switch (gameState) {
            case NOT_STARTED -> {
                layout.show(gameFrame.getContentPane(), "START");
                pauseGame();
            }
            case PLAYING -> {
                layout.show(gameFrame.getContentPane(), "GAME");
                resumeGame();
            }
            case PAUSED -> {
                layout.show(gameFrame.getContentPane(), "PAUSE");
                pauseGame();
            }
        }
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
            if (gameState == GameState.PAUSED) {
                continue;
            }

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1_000_000_000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(){

    }

    public void paintComponent(Graphics2D g){
        super.paintComponent(g);

        gameView.paintComponent(g);

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
