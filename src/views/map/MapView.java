package views.map;

import game.GameSettings;
import controllers.map.MapController;
import models.map.Country;
import models.game.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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


        Color oceanColor = new Color(0, 51, 102, 255); // Alpha 255 = pełna nieprzezroczystość

        this.setBackground(oceanColor);


        this.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println("DUPA");
                scaleMap();
            }
        });
    }

    public void initializeCountries(){
        for( Country country : gameModel.getCountries()){
            JPanel countryPanel = new JPanel();

            countryPanel.setBackground(country.getColor().getColor());
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
        System.out.println("HEHEHEHHEHE cOUNTRY CLICK");
        gameModel.getCountries().forEach(country -> country.setSelected(false));

        selectedCountry.setSelected(true);

//        countryPanels.forEach((country, panel) -> {
//            panel.setBackground(country.isSelected() ? Color.RED : Color.GREEN);
//        });

        mapController.handleMapClick(selectedCountry.getMapObjectPosition());
        revalidate();
        repaint();
    }

    private void scaleMap() {
        double scaleX = (double) getWidth() / GameSettings.mapWidth;
        double scaleY = (double) getHeight() / GameSettings.windowHeight;

        for (Map.Entry<Country, JPanel> entry : countryPanels.entrySet()) {
            Country country = entry.getKey();
            JPanel countryPanel = entry.getValue();

            if (countryPanel != null) {
                int scaledX = (int) (country.getMapObjectX() * scaleX);
                int scaledY = (int) (country.getMapObjectY() * scaleY);
                int scaledWidth = (int) (country.getWidth() * scaleX);
                int scaledHeight = (int) (country.getHeight() * scaleY);

                countryPanel.setBounds(scaledX, scaledY, scaledWidth, scaledHeight);
            }
        }

        revalidate();
        repaint();
    }



}
