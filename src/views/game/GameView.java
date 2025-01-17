package views.game;

import util.GameSettings;
import controllers.map.MapController;

import models.game.GameModel;

import views.map.MapView;



import javax.swing.*;
import java.awt.*;


public class GameView extends JPanel  {
    private final GameModel gameModel;

    private final GameHeaderView gameHeaderView;
    private final GameStatisticsView statisticsPanel;
    private final GameCountryView gameCountryView;
    private final MapView mapView;


    public GameView( GameModel gameModel, MapController mapController) {
        this.gameModel = gameModel;

        this.setLayout(new BorderLayout());

        this.setBackground(GameSettings.mainBackgroundGrey);

        gameHeaderView = new GameHeaderView( gameModel);
        this.add(gameHeaderView, BorderLayout.NORTH);

        mapView = new MapView(gameModel, mapController);
        this.add(mapView, BorderLayout.CENTER);
        gameModel.addObserver(mapView);


        JPanel sidebarPanel = new JPanel(new BorderLayout());
        sidebarPanel.setBackground(GameSettings.mainBackgroundGrey);
        sidebarPanel.setPreferredSize(new Dimension(GameSettings.sidebarWidht, 0));
        sidebarPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));

        statisticsPanel = new GameStatisticsView();
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
        SwingUtilities.invokeLater(()->{
            JOptionPane.showMessageDialog(
                    this,
                    "W prowincji Huber wybuchła pozornie niewinna epidemia.\n" +
                            "Rozprzestrzenia się szybciej, niż ktokolwiek mógł przypuszczać.",
                    "Początek epidemii",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

    }










}
