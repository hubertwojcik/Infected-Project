package controllers;

import controllers.game.GameController;
import controllers.gameEnd.GameEndController;
import enums.DifficultyLevel;
import util.GameSettings;
import util.ViewsManager;
import models.highScores.HighScoresModel;
import models.game.GameEndModel;
import views.game.GameEndView;
import views.game.GameFrame;
import views.game.GameView;
import views.highScores.HighScoresView;
import views.selectDifficulty.SelectDifficultyLevelView;
import views.game.StartGameView;

import javax.swing.*;


public class AppController extends JPanel
{
    public GameFrame gameFrame;
    private final ViewsManager screenManager;


    public AppController() {
        gameFrame = new GameFrame();
        screenManager = new ViewsManager(gameFrame);
        initializeScreens();

        gameFrame.setVisible(true);
    }

    private void initializeScreens() {
        screenManager.addScreen("START", new StartGameView(this));
        screenManager.addScreen("SELECT_DIFFICULTY", new SelectDifficultyLevelView(this));
    }


    public void showDifficultySelection() {
        screenManager.showScreen("SELECT_DIFFICULTY");
    }

    public void showHighScoresView() {
        HighScoresModel highScoresModel  = new HighScoresModel();
        screenManager.addScreen("HIGH_SCORES", new HighScoresView(this,highScoresModel));
        screenManager.showScreen("HIGH_SCORES");
    }

    public void showHomeView() {
        screenManager.showScreen("START");
    }


    public void showGameEndView(int duration, int recovered, int dead){
        GameEndModel gameEndModel = new GameEndModel();
        GameEndController gameEndController = new GameEndController(this,gameEndModel);
        screenManager.removeScreen("GAME_END");
        screenManager.addScreen("GAME_END", new GameEndView(gameEndController,duration, recovered, dead));
        screenManager.showScreen("GAME_END");
    }

    public void startNewGame(DifficultyLevel difficultyLevel) {

        GameSettings.setDifficultyLevel(difficultyLevel);

        GameController gameController = new GameController(this );
        GameView gameView = gameController.getGameView();

        screenManager.removeScreen("GAME");
        screenManager.addScreen("GAME", gameView);
        screenManager.showScreen("GAME");

        gameController.startNewGame();
    }

}
