package views.game;

import components.StyledDetailLabel;
import components.UpgradeInformations;
import enums.TransportType;
import util.GameSettings;
import interfaces.Upgrade;
import models.country.Country;
import interfaces.GameObserver;
import models.game.GameModel;
import util.Helpers;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class GameCountryView extends JPanel implements GameObserver {
    private final GameModel gameModel;
    private final JLabel countryLabel;
    private final JPanel upgradesPanel;
    private final StyledDetailLabel countryPointsLabel;
    private final StyledDetailLabel populationLabel;
    private final StyledDetailLabel suspectibleLabel;
    private final StyledDetailLabel infectedLabel;
    private final StyledDetailLabel recoveredLabel;
    private final StyledDetailLabel deadLabel;
    private final StyledDetailLabel infectedRateLabel;
    private final StyledDetailLabel recoveryResistanceLabel;
    private final StyledDetailLabel mortalityRateLabel;

    public GameCountryView(GameModel gameModel) {
        this.gameModel = gameModel;
        this.setLayout(new BorderLayout());
        this.setBackground(GameSettings.mainBackgroundGrey);


        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
        headerPanel.setBackground(GameSettings.mainBackgroundGrey);

        countryLabel = new JLabel("No country selected");
        countryLabel.setFont(new Font("Arial", Font.BOLD, 18));
        countryLabel.setForeground(Color.WHITE);
        headerPanel.add(countryLabel);

        this.add(headerPanel, BorderLayout.NORTH);


        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        statsPanel.setBackground(GameSettings.mainBackgroundGrey);

        countryPointsLabel = new StyledDetailLabel("Punkty:", "0", Color.WHITE, Color.WHITE);
        populationLabel = new StyledDetailLabel("Populacja:", "0", Color.WHITE, Color.WHITE);
        suspectibleLabel = new StyledDetailLabel("Podatni:", "0", Color.WHITE, Color.WHITE);
        infectedLabel = new StyledDetailLabel("Zarażeni:", "0", Color.WHITE, Color.WHITE);
        recoveredLabel = new StyledDetailLabel("Ozdrowieni:", "0", Color.WHITE, Color.WHITE);
        deadLabel = new StyledDetailLabel("Martwi:", "0", Color.WHITE, Color.WHITE);
        infectedRateLabel = new StyledDetailLabel("Zarażalność:", "0", Color.WHITE, Color.WHITE);
        recoveryResistanceLabel = new StyledDetailLabel("Skuteczność leczenia:", "0", Color.WHITE, Color.WHITE);
        mortalityRateLabel = new StyledDetailLabel("Śmiertelność:", "0", Color.WHITE, Color.WHITE);

        statsPanel.add(countryPointsLabel);
        statsPanel.add(populationLabel);
        statsPanel.add(suspectibleLabel);
        statsPanel.add(infectedLabel);
        statsPanel.add(recoveredLabel);
        statsPanel.add(deadLabel);
        statsPanel.add(infectedRateLabel);
        statsPanel.add(recoveryResistanceLabel);
        statsPanel.add(mortalityRateLabel);

        JPanel statsContainer = new JPanel(new BorderLayout());
        statsContainer.add(statsPanel, BorderLayout.CENTER);
        statsContainer.setPreferredSize(new Dimension(300, 250));
        this.add(statsContainer, BorderLayout.CENTER);


        upgradesPanel = new JPanel();
        upgradesPanel.setLayout(new BoxLayout(upgradesPanel, BoxLayout.Y_AXIS));
        upgradesPanel.setPreferredSize(new Dimension(300, 2000));

        JScrollPane upgradesScrollPane = new JScrollPane(upgradesPanel);
        upgradesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        upgradesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        upgradesScrollPane.setPreferredSize(new Dimension(300, 350));

        this.add(upgradesScrollPane, BorderLayout.SOUTH);


    }

    @Override
    public void onSelectedCountryUpdate(String countryName, double countryPoints, int population, int suspectible, int infected, int cured, int dead, double infectedRate, double recoveryRestinatce, double moratyliRate) {
        if (countryName == null) {
            SwingUtilities.invokeLater(() -> this.setVisible(false));
        } else {
            SwingUtilities.invokeLater(() -> {
                this.setVisible(true);
                countryLabel.setText(countryName);
                countryPointsLabel.setValue("" + Helpers.roundToTwoDecimals(countryPoints));
                populationLabel.setValue("" + population);
                suspectibleLabel.setValue("" + suspectible);
                infectedLabel.setValue("" + infected);
                recoveredLabel.setValue("" + cured);
                deadLabel.setValue("" + dead);
                infectedRateLabel.setValue("" + Helpers.roundToTwoDecimals(infectedRate));
                recoveryResistanceLabel.setValue("" + Helpers.roundToTwoDecimals(recoveryRestinatce));
                mortalityRateLabel.setValue("" + Helpers.roundToTwoDecimals(moratyliRate));

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

    @Override
    public void onGameEnd() {
    }

    @Override
    public void onTransportStateUpdate(Country country, TransportType transportType, boolean isEnabled) {
    }

    private void updateUpgradesPanel(Map<Upgrade, Boolean> upgrades) {
        upgradesPanel.removeAll();

        for (Map.Entry<Upgrade, Boolean> entry : upgrades.entrySet()) {
            Upgrade upgrade = entry.getKey();
            boolean isBought = entry.getValue();

            UpgradeInformations upgradeInformation = new UpgradeInformations(upgrade, isBought, () -> {
                Country selectedCountry = gameModel.getSelectedCountry();
                if (selectedCountry == null) return;

                if (selectedCountry.getCountryPoints() >= upgrade.getCost()) {
                    upgrade.applyUpgrade(selectedCountry);
                    updateUpgradesPanel(selectedCountry.getUpgrades());
                    JOptionPane.showMessageDialog(this, "Kupiono ulepszenie: " + upgrade.getName());
                } else {
                    JOptionPane.showMessageDialog(this, "Zbyt mało punktów!", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            });

            upgradesPanel.add(upgradeInformation);
        }
        upgradesPanel.revalidate();
        upgradesPanel.repaint();
    }

}
