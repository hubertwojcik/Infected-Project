package views;

import controllers.GameController;
import controllers.MapController;
import javax.swing.*;
import java.awt.*;
import models.GameModel;

public class GameView extends JPanel {
    private final MapView mapView;

    //VIEWS
    private final GameHeaderView gameHeaderView;
    private GameSidebarView gameSidebarView;

    public GameView(GameController gameController, GameModel gameModel, MapController mapController) {
        //MODELS
        //CONTROLLERS
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

    public void updateGameViews() {
            gameHeaderView.updateGameDateLabel();;
            gameSidebarView.updateCountryPanel();
    }


}
