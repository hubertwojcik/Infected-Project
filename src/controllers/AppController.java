package controllers;

import controllers.game.GameController;
import enums.GameState;
import game.GameSettings;
import game.ScreenManager;
import views.game.GameFrame;
import views.game.GameView;
import views.general.HighScoresView;
import views.general.SelectDifficultyLevelView;
import views.general.StartGameView;

import javax.swing.*;
import java.awt.*;

public class AppController extends JPanel
{
    // CONTROLLERS

    public GameController gameController;
    // GAME SCREENS
    public StartGameView startGameView;
    public SelectDifficultyLevelView selectDifficultyLevelView;
    public HighScoresView highScoresView;
    //VIEWS
    public GameFrame gameFrame;
    private final ScreenManager screenManager;

    public AppController() {
        gameFrame = new GameFrame();
        screenManager = new ScreenManager(gameFrame);

        initializeScreens();

        gameFrame.setVisible(true);
    }

    private void initializeScreens() {
        screenManager.addScreen("START", new StartGameView(this));
        screenManager.addScreen("SELECT_DIFFICULTY", new SelectDifficultyLevelView(this));
        screenManager.addScreen("HIGH_SCORES", new HighScoresView(this));
    }


    public void showDifficultySelection() {
        screenManager.showScreen("SELECT_DIFFICULTY");
    }

    public void showHighScoresView() {
        screenManager.showScreen("HIGH_SCORES");
    }

    public void showHomeView() {
        screenManager.showScreen("START");
    }

//    public AppController(){
//
//        //MODELS
//        // CONTROLLERS
//
//        gameController = new GameController(this);
//        // VIEWS
//        gameFrame = new GameFrame();
//        startGameView = new StartGameView(this);
//        selectDifficultyLevelView = new SelectDifficultyLevelView(this);
//        highScoresView = new HighScoresView(this);
//
//
//        gameFrame.add(startGameView,"START");
//        gameFrame.add(selectDifficultyLevelView,"SELECT_DIFFICULTY");
//
//        gameFrame.add(highScoresView,"HIGH_SCORES");
//
//
//        gameFrame.setVisible(true);
//    }

    public void setAppState(GameState gameState) {
        CardLayout layout = (CardLayout) gameFrame.getContentPane().getLayout();
        switch (gameState) {
            case NOT_STARTED -> layout.show(gameFrame.getContentPane(), "START");
            case SELECT_DIFFICULTY -> layout.show(gameFrame.getContentPane(),"SELECT_DIFFICULTY");
            case PLAYING -> layout.show(gameFrame.getContentPane(), "GAME");
            case HIGH_SCORES -> layout.show(gameFrame.getContentPane(),"HIGH_SCORES");

        }
    }


    public void startNewGame() {
        GameController gameController = new GameController(this, GameSettings.getDifficultyLevel());
        GameView gameView = gameController.getGameView();

        screenManager.removeScreen("GAME");
        screenManager.addScreen("GAME", gameView);
        screenManager.showScreen("GAME");

        gameController.startNewGame();

//        gameController.resetGame();
//        setAppState(GameState.PLAYING);
//        gameController.startNewGame();


    }


    public void stopGame() {
        setAppState(GameState.NOT_STARTED);
        gameController.stopGame();
    }

//    public void showDifficultySelection() {
//        setAppState(GameState.SELECT_DIFFICULTY);
//    }

//    public void showHighScoresView() {
//        setAppState(GameState.HIGH_SCORES);
//    }

//    public void showHomeView() {
//        setAppState(GameState.NOT_STARTED);
//    }


}
