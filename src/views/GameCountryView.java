package views;

import models.Country;
import models.GameModel;

import javax.swing.*;
import java.awt.*;

public class GameCountryView extends JPanel {
    private final GameModel gameModel;
    private JLabel countryLabel;

    public GameCountryView(GameModel gameModel){
        this.gameModel = gameModel;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


    }

    public void updateCountryPanel() {
        this.removeAll();
        Country selectedCountry = gameModel.getSelectedCountry();
        if (selectedCountry == null) {
            this.add(new JLabel("No country selected"));
        } else {
            countryLabel = new JLabel( selectedCountry.getName());
            countryLabel.setHorizontalAlignment(JLabel.CENTER);
            this.add(countryLabel);

        }



    }

}
