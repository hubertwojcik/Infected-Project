package controllers;

import enums.GameState;
import views.*;
import views.GameFrame;
import javax.swing.*;
import java.awt.*;

public class AppController extends JPanel
{
    private GameState gameState = GameState.NOT_STARTED;
    // CONTROLLERS
    public PauseController pauseController;
    public GameController gameController;
    // GAME SCREENS
    public StartGameView startGameView;
    public SelectDifficultyLevelView selectDifficultyLevelView;
    public HighScoresView highScoresView;
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
        selectDifficultyLevelView = new SelectDifficultyLevelView(this);
        highScoresView = new HighScoresView(this);

        gameFrame.add(startGameView,"START");
        gameFrame.add(selectDifficultyLevelView,"SELECT_DIFFICULTY");
        gameFrame.add(gameController.getGameView(),"GAME");
        gameFrame.add(pauseController.getPauseView(),"PAUSE");
        gameFrame.add(highScoresView,"HIGH_SCORES");


        gameFrame.setVisible(true);
    }

    public void setAppState(GameState gameState) {
        CardLayout layout = (CardLayout) gameFrame.getContentPane().getLayout();
        switch (gameState) {
            case NOT_STARTED -> layout.show(gameFrame.getContentPane(), "START");
            case SELECT_DIFFICULTY -> layout.show(gameFrame.getContentPane(),"SELECT_DIFFICULTY");
            case PLAYING -> layout.show(gameFrame.getContentPane(), "GAME");
            case PAUSED -> layout.show(gameFrame.getContentPane(), "PAUSE");
            case HIGH_SCORES -> layout.show(gameFrame.getContentPane(),"HIGH_SCORES");
        }
    }


    public void startNewGame() {
        gameController.resetGame();
        setAppState(GameState.PLAYING);
        gameController.startNewGame();
    }

    public void resumeGame() {
        if (gameState == GameState.PAUSED) {
            setAppState(GameState.PLAYING);
            gameController.resumeGame();
        }
    }

    public void pauseGame() {
        if (gameState == GameState.PLAYING) {
            setAppState(GameState.PAUSED);
            gameController.pauseGame();
        }
    }

    public void stopGame() {
        setAppState(GameState.NOT_STARTED);
        gameController.stopGame();
    }

    public void showDifficultySelection() {
        setAppState(GameState.SELECT_DIFFICULTY);
    }

    public void showHighScoresView() {
        setAppState(GameState.HIGH_SCORES);
    }

    public void showHomeView() {
        setAppState(GameState.NOT_STARTED);
    }


}
