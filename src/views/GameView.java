package views;

import controllers.AppController;
import controllers.MapController;
import enums.GameState;
import models.AppModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import models.CountryRectangle;
import models.GameModel;

public class GameView extends JPanel {

    private final GameModel gameModel;
    private final AppController appController;
    private final MapView mapView;

    //Views
    private final UpgradeCountryView upgradeCountryView;
    private final GameHeaderView gameHeaderView;

    // Lables



    public GameView( AppController appController,GameModel gameModel, MapController mapController) {
        //Models
        this.gameModel = gameModel;
        //Controlles
        this.appController = appController;
        //Views
        this.setLayout(new BorderLayout());


        gameHeaderView = new GameHeaderView(appController, gameModel);
        // Top Panel
        this.add(gameHeaderView, BorderLayout.NORTH);

        mapView = new MapView(gameModel, mapController);


        JPanel sidebar = new JPanel(new BorderLayout());
        // Panel ulepszeń
        upgradeCountryView = new UpgradeCountryView();
        this.add(upgradeCountryView, BorderLayout.EAST);
        this.add(mapView, BorderLayout.CENTER);



    }

    public void updateDate() {
            gameHeaderView.updateGameDateLabel();;
    }

//    @Override
//    public void paintComponent(Graphics g) {
//      mapView.paintComponent(g);
//
//    }
}
