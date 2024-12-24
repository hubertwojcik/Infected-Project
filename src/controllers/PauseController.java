package controllers;

import enums.GameState;
import views.PauseView;

public class PauseController {
    //CONTROLLERS
    private final AppController appController;
    //VIEWS
    PauseView pauseView;

    public PauseController(AppController appController) {
        this.appController = appController;
        pauseView = new PauseView(this);
    }

    public PauseView getPauseView(){
        return pauseView;
    }
    public void resumeGame() {

        appController.setGameState(GameState.PLAYING);
        appController.resumeGame();

    }

    public void exitGame(){
        System.exit(0);
    }
}
