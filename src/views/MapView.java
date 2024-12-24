package views;

import controllers.MapController;
import models.Country;
import models.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.HashMap;
import java.util.Map;

public class MapView extends JPanel {
    private final GameModel gameModel;
    private final MapController mapController;

    private final Map<Country, JPanel> countryPanels = new HashMap<>();


    public MapView(GameModel gameModel,MapController mapController){
        this.gameModel = gameModel;
        this.mapController = mapController;
        this.setLayout(null);
        initializeCountries();
    }

    public void initializeCountries(){
        for( Country country : gameModel.getCountries()){
            JPanel countryPanel = new JPanel();

            countryPanel.setBackground(Color.GREEN);
            countryPanel.setBackground(Color.GREEN);
            countryPanel.setBounds(
                    country.getMapObjectX(),
                    country.getMapObjectY(),
                    country.getWidth(),
                    country.getHeight()
            );
            countryPanel.setToolTipText(country.getName());

            countryPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                    handleCountryClick(country);
                }
            });

            this.add(countryPanel);
            countryPanels.put(country, countryPanel);
        }
    }

    private void handleCountryClick(Country selectedCountry) {
        gameModel.getCountries().forEach(country -> country.setSelected(false));

        selectedCountry.setSelected(true);

        countryPanels.forEach((country, panel) -> {
            panel.setBackground(country.isSelected() ? Color.RED : Color.GREEN);
        });

        mapController.handleMapClick(selectedCountry.getMapObjectPosition());
    }


}
