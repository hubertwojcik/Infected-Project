package views.game;

import components.StyledDetailLabel;
import enums.TransportType;
import util.GameSettings;
import models.country.Country;
import interfaces.GameObserver;

import javax.swing.*;
import java.awt.*;

public class GameStatisticsView extends JPanel implements GameObserver {

    private final StyledDetailLabel infectedLabel;
    private final StyledDetailLabel curedLabel;
    private final StyledDetailLabel deadLabel;

    public GameStatisticsView() {
        this.setLayout(new GridLayout(3, 1, 0, 10));
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE),
                BorderFactory.createEmptyBorder(10, 0, 10, 0)
        ));        this.setBackground(GameSettings.mainBackgroundGrey);

        infectedLabel = new StyledDetailLabel("Zarażeni:", "0", Color.WHITE, Color.CYAN);
        curedLabel = new StyledDetailLabel("Ozdrowieńcy:", "0", Color.WHITE, Color.GREEN);
        deadLabel = new StyledDetailLabel("Martwi:", "0", Color.WHITE, Color.RED);

        this.add(infectedLabel);
        this.add(curedLabel);
        this.add(deadLabel);
    }

    @Override
    public void onGlobalStatsUpdate(int infected, int cured, int dead) {
        SwingUtilities.invokeLater(() -> {
            infectedLabel.setValue(String.valueOf(infected));
            curedLabel.setValue(String.valueOf(cured));
            deadLabel.setValue(String.valueOf(dead));
        });
    }

    @Override
    public void onSelectedCountryUpdate(String countryName, double points, int population, int suspectible, int infected, int cured, int dead, double infectedRate, double recoveryRestinatce, double moratyliRate) {

    }


    @Override
    public void onDayUpdate(int dayCounter) {

    }

    @Override
    public void onGameEnd() {

    }

    @Override
    public void onTransportStateUpdate(Country country, TransportType transportType, boolean isEnabled) {

    }
}
