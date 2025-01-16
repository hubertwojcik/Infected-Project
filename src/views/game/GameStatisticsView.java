package views.game;


import enums.TransportType;
import models.country.Country;
import models.game.GameModel;
import models.game.GameObserver;

import javax.swing.*;
import java.awt.*;

public class GameStatisticsView extends JPanel implements GameObserver {

    private final JLabel infectedLabel;
    private final JLabel curedLabel;
    private final JLabel deadLabel;

    public GameStatisticsView() {
        this.setLayout(new GridLayout(3, 1, 0, 5));
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        this.setBackground(Color.white);

        this.add(createAlignedRow("Zarażeni:", infectedLabel = createStyledLabel("0")));
        this.add(createAlignedRow("Ozdrowieńcy:", curedLabel = createStyledLabel("0")));
        this.add(createAlignedRow("Martwi:", deadLabel = createStyledLabel("0")));

    }

    @Override
    public void onSelectedCountryUpdate(String countryName, double points,int population,int suspectible, int infected, int cured, int dead,double infectedRate,double recoveryRestinatce, double moratyliRate) {
    }

    @Override
    public void onGlobalStatsUpdate(int infected, int cured, int dead) {
        SwingUtilities.invokeLater(()->{});
            infectedLabel.setText("" + infected);
            curedLabel.setText("" + cured);
            deadLabel.setText("" + dead);
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

    private JPanel createAlignedRow(String labelText, JLabel valueLabel) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        rowPanel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));

        rowPanel.add(label);
        rowPanel.add(valueLabel);
        return rowPanel;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }
}
