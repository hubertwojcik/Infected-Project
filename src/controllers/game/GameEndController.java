package controllers.game;

import controllers.AppController;
import models.game.GameEndModel;

import java.io.*;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

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
        }
    }
}
