package views.game;

import enums.TransportType;
import interfaces.Upgrade;
import models.country.Country;
import models.game.GameObserver;
import models.game.GameModel;
import util.Helpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;

public class GameCountryView extends JPanel implements GameObserver {
    private final GameModel gameModel;
    private final JLabel countryLabel;
    private final JLabel countryPointsLabel;
    private final JLabel populationLabel;
    private final JLabel suspectibleLabel;
    private final JLabel infectedLabel;
    private final JLabel recoveredLabel;
    private final JLabel deadLabel;
    private final JPanel upgradesPanel;
    private final JLabel infectedRateLabel;
    private final JLabel recoveryResistanceLabel;
    private final JLabel mortalityRateLabel;


    public GameCountryView(GameModel gameModel) {
        this.gameModel = gameModel;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        headerPanel.setBackground(Color.white);

        countryLabel = new JLabel("No country selected");
        countryLabel.setFont(new Font("Arial", Font.BOLD, 18));
        countryLabel.setForeground(new Color(50, 50, 50));
        headerPanel.add(countryLabel);

        this.add(headerPanel, BorderLayout.NORTH);


        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        statsPanel.setBackground(Color.WHITE);

        statsPanel.add(createAlignedRow("Punkty:", countryPointsLabel = createStyledLabel("0")));
        statsPanel.add(createAlignedRow("Populacja:", populationLabel = createStyledLabel("0")));
        statsPanel.add(createAlignedRow("Podatni:", suspectibleLabel = createStyledLabel("0")));
        statsPanel.add(createAlignedRow("Zarażeni:", infectedLabel = createStyledLabel("0")));
        statsPanel.add(createAlignedRow("Ozdrowieni:", recoveredLabel = createStyledLabel("0")));
        statsPanel.add(createAlignedRow("Martwi:", deadLabel = createStyledLabel("0")));
        statsPanel.add(createAlignedRow("Zarażalność:", infectedRateLabel = createStyledLabel("0")));
        statsPanel.add(createAlignedRow("Skuteczność leczenia:", recoveryResistanceLabel = createStyledLabel("0")));
        statsPanel.add(createAlignedRow("Śmiertelność:", mortalityRateLabel = createStyledLabel("0")));

        JPanel statsContainer = new JPanel(new BorderLayout());
        statsContainer.add(statsPanel, BorderLayout.CENTER);
        statsContainer.setPreferredSize(new Dimension(300, 250));
        this.add(statsContainer, BorderLayout.CENTER);

        upgradesPanel = new JPanel();
        upgradesPanel.setLayout(new BoxLayout(upgradesPanel, BoxLayout.Y_AXIS));
        upgradesPanel.setPreferredSize(new Dimension(300, 1535));

        JScrollPane upgradesScrollPane = new JScrollPane(upgradesPanel);
        upgradesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        upgradesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        upgradesScrollPane.setPreferredSize(new Dimension(300, 300));

        this.add(upgradesScrollPane, BorderLayout.SOUTH);
    }

    @Override
    public void onSelectedCountryUpdate(String countryName, double countryPoints, int population,int suspectible, int infected, int cured, int dead, double infectedRate,double recoveryRestinatce, double moratyliRate) {
        if (countryName == null) {
            SwingUtilities.invokeLater(() -> this.setVisible(false));
        } else {
            SwingUtilities.invokeLater(() -> {
                this.setVisible(true);
                countryLabel.setText(countryName);
                countryPointsLabel.setText("" + Helpers.roundToTwoDecimals(countryPoints));
                populationLabel.setText(""+population);
                suspectibleLabel.setText(""+ suspectible);
                infectedLabel.setText("" + infected);
                recoveredLabel.setText("" + cured);
                deadLabel.setText("" + dead);
                infectedRateLabel.setText("" +  Helpers.roundToTwoDecimals(infectedRate) );
                recoveryResistanceLabel.setText(""+  Helpers.roundToTwoDecimals(recoveryRestinatce));
                mortalityRateLabel.setText("" + Helpers.roundToTwoDecimals(moratyliRate));


                Country selectedCountry = gameModel.getSelectedCountry();
                if (selectedCountry != null) {
                    updateUpgradesPanel(selectedCountry.getUpgrades());
                }
            });
        }
    }

    @Override
    public void onGlobalStatsUpdate(int infected, int cured, int dead) {
    }

    @Override
    public void onDayUpdate(int dayCounter) {
    }

    private void updateUpgradesPanel(Map<Upgrade, Boolean> upgrades) {
        upgradesPanel.removeAll();

        for (Map.Entry<Upgrade, Boolean> entry : upgrades.entrySet()) {
            Upgrade upgrade = entry.getKey();
            boolean isBought = entry.getValue();

            JPanel upgradePanel = new JPanel();
            upgradePanel.setBackground(Color.white);
            upgradePanel.setLayout(new BoxLayout(upgradePanel, BoxLayout.Y_AXIS));
            upgradePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            upgradePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));

            JLabel upgradeLabel = new JLabel(upgrade.getName()+ ": " + upgrade.getCost() +" pkt");
            upgradeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            upgradeLabel.setFont(new Font("Arial", Font.BOLD, 14));

            JPanel detailsPanel = new JPanel();
            detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
            detailsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            detailsPanel.setOpaque(false);

            for (Map.Entry<String, Double> effect : upgrade.getEffects().entrySet()) {
                JLabel effectLabel = new JLabel(effect.getKey() + ": " + effect.getValue());
                effectLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                detailsPanel.add(effectLabel);
            }

            JButton buyButton = new JButton("Kup");
            buyButton.setAlignmentX(Component.LEFT_ALIGNMENT);

            if (isBought) {
                buyButton.setEnabled(false);
                buyButton.setText("Kupione");
            }else{
                for (ActionListener al : buyButton.getActionListeners()) {
                    buyButton.removeActionListener(al);
                }


            buyButton.addActionListener(e -> {
                Country selectedCountry = gameModel.getSelectedCountry();
                if (selectedCountry == null) return;

                if (selectedCountry.getCountryPoints() >= upgrade.getCost()) {
                upgrade.applyUpgrade(selectedCountry);
                buyButton.setEnabled(false);
                buyButton.setText("Kupione");
                JOptionPane.showMessageDialog(this, "Kupiono ulepszenie: " + upgrade.getName());
                    System.out.println("Dodaję upgrade: " + upgrade.getName() + " | Koszt: " + upgrade.getCost());

                } else {
                    JOptionPane.showMessageDialog(this, "Nie stać Cię na to ulepszenie!", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            });
            }

            upgradePanel.add(upgradeLabel);
            upgradePanel.add(detailsPanel);
            upgradePanel.add(Box.createVerticalStrut(10));
            upgradePanel.add(buyButton);

            upgradesPanel.add(upgradePanel);
        }


    }

    private JPanel createAlignedRow(String labelText, JLabel valueLabel) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
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

    @Override
    public void onGameEnd() {

    }

    @Override
    public void onTransportStateUpdate(Country country, TransportType transportType, boolean isEnabled) {

    }
}
