package controllers;

import enums.GameState;
import models.AppModel;
import models.GameModel;
import views.*;
import views.GameFrame;
import javax.swing.*;
import java.awt.*;

public class AppController extends JPanel implements Runnable{
    private boolean isRunning = false;
    private int FPS = 60;
    private double timeElapsed = 0;

    //Models
    private final AppModel appModel;
    private final GameModel gameModel;

    // CONTROLLERS
    public MapController mapController;

    // GAME SCREENS
    public GameView gameView;
    public StartGameView startGameView;
    public PauseView pauseView;

    //Views
    public GameFrame gameFrame;


    public AppController(){
        //MODELS
        appModel = new AppModel();
        gameModel = new GameModel();
        // CONTROLLERS
        mapController = new MapController(appModel);
        // VIEWS
        gameFrame = new GameFrame();

        startGameView = new StartGameView(this);
        gameView = new GameView(appModel,this,gameModel,mapController);
        pauseView = new PauseView(this);

        gameFrame.add(startGameView,"START");
        gameFrame.add(gameView,"GAME");
        gameFrame.add(pauseView,"PAUSE");

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
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(){
        //Update Day
        if (timeElapsed >= 5) {
            System.out.println("TIME ELAPSED");
            gameModel.advanceOneDay();
            gameView.updateDate();
            timeElapsed = 0;
        }
    }

    public void paintComponent(Graphics2D g){
        super.paintComponent(g);

//        gameView.paintComponent(g);

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
