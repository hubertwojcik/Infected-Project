package controllers;

import enums.GameState;
import models.AppModel;
import views.*;
import views.GameFrame;
import javax.swing.*;
import java.awt.*;

public class AppController extends JPanel

{
    // CONTROLLERS
    public PauseController pauseController;
    public GameController gameController;
    // GAME SCREENS
    public StartGameView startGameView;
    //VIEWS
    public GameFrame gameFrame;

    public AppController(){
        //MODELS
//        appModel = new AppModel();
        // CONTROLLERS
        pauseController = new PauseController(this);
        gameController = new GameController(this);
        // VIEWS
        gameFrame = new GameFrame();
        startGameView = new StartGameView(this);

        gameFrame.add(startGameView,"START");
        gameFrame.add(gameController.getGameView(),"GAME");
        gameFrame.add(pauseController.getPauseView(),"PAUSE");


        gameFrame.setVisible(true);
    }

    public void setGameState(GameState gameState) {

        CardLayout layout = (CardLayout) gameFrame.getContentPane().getLayout();

        switch (gameState) {
            case NOT_STARTED -> layout.show(gameFrame.getContentPane(), "START");
            case PLAYING -> layout.show(gameFrame.getContentPane(), "GAME");
            case PAUSED -> layout.show(gameFrame.getContentPane(), "PAUSE");
        }
    }



    public void startGame() {
        setGameState(GameState.PLAYING);
        gameController.startGame();
    }

    public void pauseGame() {
        setGameState(GameState.PAUSED);
        gameController.pauseGame();
    }

    public void stopGame() {
        setGameState(GameState.NOT_STARTED);
        gameController.stopGame();
    }




}
