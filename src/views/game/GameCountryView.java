package views.game;

import models.game.GameObserver;
import models.map.Country;
import models.game.GameModel;

import javax.swing.*;

public class GameCountryView extends JPanel implements GameObserver {
    private final GameModel gameModel;
    private JLabel countryLabel;
    private JLabel populationLabel;
    private JLabel infectedLabel;
    private JLabel recoveredLabel;
    private JLabel deadLabel;

    public GameCountryView(GameModel gameModel) {
        this.gameModel = gameModel;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Nagłówek kraju
        countryLabel = new JLabel("No country selected");
        countryLabel.setAlignmentX(CENTER_ALIGNMENT);
        this.add(countryLabel);

        // Tabela z danymi



        // Nagłówki kolumn


        // Dane dynamiczne
        populationLabel = new JLabel("Population: ");
        infectedLabel = new JLabel("Infected: ", SwingConstants.CENTER);
        recoveredLabel = new JLabel("Recovered: ", SwingConstants.CENTER);
        deadLabel = new JLabel("Dead: ", SwingConstants.CENTER);

        this.add(populationLabel);
        this.add(infectedLabel);
        this.add(recoveredLabel);
        this.add(deadLabel);


    }



    @Override
    public void onSelectedCountryUpdate(String countryName, int population, int infected, int cured, int dead) {
        if (countryName == null) {
            SwingUtilities.invokeLater(() -> this.setVisible(false));
        } else {
            SwingUtilities.invokeLater(() -> {
                this.setVisible(true);
                countryLabel.setText("Country: " + countryName);
                populationLabel.setText("Populacja: " + population);
                infectedLabel.setText("Infected: " + infected);
                recoveredLabel.setText("Cured: " + cured);
                deadLabel.setText("Dead: " + dead);
            });
        }
    }

    @Override
    public void onGlobalStatsUpdate(int infected, int cured, int dead) {

    }

    @Override
    public void onDayUpdate(int dayCounter) {

    }
}
