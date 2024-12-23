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
    private final AppModel appModel;
    private final GameModel gameModel;
    private final AppController appController;
    private final MapView mapView;

    private final UpgradeCountryView upgradeCountryView;

    // Lables
    private final JLabel dateLabel; // Etykieta do wyświetlania daty


    public GameView(AppModel appModel, AppController appController,GameModel gameModel, MapController mapController) {
        //Models
        this.appModel = appModel;
        this.gameModel = gameModel;
        //Controlles
        this.appController = appController;
        //Views
        this.setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        this.add(topPanel,BorderLayout.NORTH);

        // Przycisk pauzy
        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> {
            appController.pauseGame();
            appController.setGameState(GameState.PAUSED);
        });
        topPanel.add(pauseButton,BorderLayout.WEST);
        //Current Day label
        dateLabel = new JLabel("Date: " + gameModel.getGameDate());

        topPanel.add(dateLabel,BorderLayout.CENTER);

        // Dodanie przycisku do panelu
//        this.add(pauseButton, BorderLayout.NORTH);

        mapView = new MapView(gameModel, mapController);


        JPanel sidebar = new JPanel(new BorderLayout());

        // Panel ulepszeń
        upgradeCountryView = new UpgradeCountryView();
        this.add(upgradeCountryView, BorderLayout.EAST);

        this.add(mapView, BorderLayout.CENTER);

    }

    public void updateDate() {
        dateLabel.setText("Date: " + gameModel.getGameDate());
    }

//    @Override
//    public void paintComponent(Graphics g) {
//      mapView.paintComponent(g);
//
//    }
}
