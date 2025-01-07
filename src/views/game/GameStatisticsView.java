package views.game;

import models.game.GameModel;
import models.game.GameObserver;

import javax.swing.*;
import java.awt.*;

public class GameStatisticsView extends JPanel implements GameObserver {
    private final GameModel gameModel;
    private final JLabel infectedLabel;
    private final JLabel curedLabel;
    private final JLabel deadLabel;

    public GameStatisticsView(GameModel gameModel){
        this.gameModel = gameModel;

        this.setLayout(new GridLayout(3, 1));
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        infectedLabel = new JLabel("Infected: 0");
        curedLabel = new JLabel("Cured: 0");
        deadLabel = new JLabel("Dead: 0");

        this.add(infectedLabel);
        this.add(curedLabel);
        this.add(deadLabel);
    }

    //TODO AKTUALIZACJA PO KAZDYM DNIU
    public void updateStats(int infected, int cured, int dead) {

    }

    @Override
    public void onSelectedCountryUpdate(String countryName, int population, int infected, int cured, int dead) {

    }

    @Override
    public void onGlobalStatsUpdate(int infected, int cured, int dead) {
        SwingUtilities.invokeLater(()->{});
            infectedLabel.setText("Infected: " + infected);
            curedLabel.setText("Cured: " + cured);
            deadLabel.setText("Dead: " + dead);
    }

    @Override
    public void onDayUpdate(int dayCounter) {

    }
}
