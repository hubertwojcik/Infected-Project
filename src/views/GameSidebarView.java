package views;

import config.Config;
import models.GameModel;

import javax.swing.*;
import java.awt.*;
import models.Country;
public class GameSidebarView extends JPanel {
    private final GameModel gameModel;
    private final GameStatisticsView statisticsPanel;
    private final GameCountryView gameCountryView;


    public GameSidebarView(GameModel gameModel){
        this.gameModel = gameModel;
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(Config.sidebarWidht, 0));
        this.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));

        statisticsPanel = new GameStatisticsView(gameModel);
        this.add(statisticsPanel, BorderLayout.NORTH);

        gameCountryView = new GameCountryView(gameModel);
        gameCountryView.setVisible(false);
        this.add(gameCountryView,BorderLayout.CENTER);
    }

    public void updateCountryPanel() {
        Country selectedCountry = gameModel.getSelectedCountry();

        if (selectedCountry == null) {
            gameCountryView.setVisible(false);
        } else {
            gameCountryView.setVisible(true);
            gameCountryView.updateCountryPanel();
        }
    }

    public void hideCountryPanel(){
        gameCountryView.setVisible(false);

    }


}
