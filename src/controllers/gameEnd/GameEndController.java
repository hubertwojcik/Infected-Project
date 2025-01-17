package controllers.gameEnd;

import controllers.AppController;
import models.game.GameEndModel;



public class GameEndController {
    private final AppController appController;
    private GameEndModel model;

    public GameEndController(AppController appController,GameEndModel model) {
        this.appController = appController;
        this.model = model;
    }

    public void onSaveResultClick(String userName, int score) {
        try {
            model.saveScoreToFile(userName, score);
            appController.showHighScoresView();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Zapis wyniku sięnie powiódł");
        }
    }
}
