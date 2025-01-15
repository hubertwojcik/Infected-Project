package controllers;

import controllers.game.GameController;
import game.ScreenManager;
import views.game.GameEndView;
import views.game.GameFrame;
import views.game.GameView;
import views.general.HighScoresView;
import views.general.SelectDifficultyLevelView;
import views.general.StartGameView;

import javax.swing.*;


public class AppController extends JPanel
{
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


    public void showGameEndView(){
        screenManager.removeScreen("GAME_END");
        screenManager.addScreen("GAME_END", new GameEndView());
        screenManager.showScreen("GAME_END");
    }

    public void startNewGame() {
        GameController gameController = new GameController(this );
        GameView gameView = gameController.getGameView();

        screenManager.removeScreen("GAME");
        screenManager.addScreen("GAME", gameView);
        screenManager.showScreen("GAME");

        gameController.startNewGame();
    }

}
