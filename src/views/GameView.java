package views;

import controllers.AppController;
import controllers.GameController;
import controllers.MapController;
import enums.GameState;
import models.AppModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import models.Country;
import models.CountryRectangle;
import models.GameModel;

public class GameView extends JPanel {
    private final GameModel gameModel;
    private final GameController gameController;
    private final MapView mapView;

    //VIEWS
    private final GameHeaderView gameHeaderView;
    private GameSidebarView gameSidebarView;

    public GameView(GameController gameController, GameModel gameModel, MapController mapController) {
        //MODELS
        this.gameModel = gameModel;
        //CONTROLLERS
        this.gameController = gameController;
        //Views
        this.setLayout(new BorderLayout());

        gameHeaderView = new GameHeaderView(gameController, gameModel);
        //GAME HEADER
        this.add(gameHeaderView, BorderLayout.NORTH);
        //GAME MAP
        mapView = new MapView(gameModel, mapController);
        this.add(mapView, BorderLayout.CENTER);
        //GAME SIDE BAR
        gameSidebarView = new GameSidebarView(gameModel);
        this.add(gameSidebarView,BorderLayout.EAST);
    }

    public GameSidebarView getGameSidebarView(){
        return gameSidebarView;
    }

    public void updateDate() {
            gameHeaderView.updateGameDateLabel();;
    }


}
