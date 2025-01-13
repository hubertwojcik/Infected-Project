package views.game;

import game.GameSettings;
import controllers.game.GameController;
import controllers.map.MapController;

import models.game.GameModel;

import views.map.MapView;



import javax.swing.*;
import java.awt.*;


public class GameView extends JPanel  {
    private final GameModel gameModel;

    // Komponenty widoku
    private final GameHeaderView gameHeaderView;
    private final GameStatisticsView statisticsPanel;
    private final GameCountryView gameCountryView;
    private final MapView mapView;

    private Image transportImage;


    public GameView(GameController gameController, GameModel gameModel, MapController mapController) {
        this.gameModel = gameModel;

        this.setLayout(new BorderLayout());

        // HEADER
        gameHeaderView = new GameHeaderView(gameController, gameModel);
        this.add(gameHeaderView, BorderLayout.NORTH);

        // MAP
        mapView = new MapView(gameModel, mapController);
        this.add(mapView, BorderLayout.CENTER);

        // SIDEBAR
        JPanel sidebarPanel = new JPanel(new BorderLayout());
        sidebarPanel.setPreferredSize(new Dimension(GameSettings.sidebarWidht, 0));
        sidebarPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));

        statisticsPanel = new GameStatisticsView(gameModel);
        sidebarPanel.add(statisticsPanel, BorderLayout.NORTH);

        gameCountryView = new GameCountryView(gameModel);
        gameCountryView.setVisible(false);
        sidebarPanel.add(gameCountryView, BorderLayout.CENTER);

        this.add(sidebarPanel, BorderLayout.EAST);


        registerObservers();


    }

    private void registerObservers() {
        gameModel.addObserver(gameHeaderView);
        gameModel.addObserver(statisticsPanel);
        gameModel.addObserver(gameCountryView);
    }



    public void showGameStartAlert() {
        JOptionPane.showMessageDialog(
                this,
                "W prowincji Huber wybuchła pozornie niewinna epidemia.\n" +
                        "Rozprzestrzenia się szybciej, niż ktokolwiek mógł przypuszczać.",
                "Początek epidemii",
                JOptionPane.INFORMATION_MESSAGE
        );
    }










}
