package views;

import models.Country;
import models.GameModel;

import javax.swing.*;
import java.awt.*;

public class GameCountryView extends JPanel {
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

    public void updateCountryPanel() {
        Country selectedCountry = gameModel.getSelectedCountry();
        if (selectedCountry == null) {
            countryLabel.setText("No country selected");
            populationLabel.setText("No country selected");
            infectedLabel.setText("Infected: -");
            recoveredLabel.setText("Recovered: ");
            deadLabel.setText("Dead: ");
        } else {
            countryLabel.setText(selectedCountry.getName());
            populationLabel.setText("Populacja: "+ selectedCountry.getPopulation());
            infectedLabel.setText("Infected: " + (selectedCountry.getInfected()));
            recoveredLabel.setText("Recovered: "+(selectedCountry.getRecovered()));
            deadLabel.setText("Dead: "+(selectedCountry.getDead()));
        }

        // Odśwież widok
        this.revalidate(); // Przebuduj layout
        this.repaint(); // Odśwież widok
    }
}
