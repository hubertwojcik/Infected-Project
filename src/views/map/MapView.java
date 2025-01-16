package views.map;

import enums.TransportType;
import game.GameSettings;
import controllers.map.MapController;
import models.Transport.Transport;
import models.country.Country;
import models.game.GameModel;
import models.game.GameObserver;
import views.transport.TransportIconView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapView extends JPanel implements GameObserver {
    private final Map<TransportType, Boolean> transportStates = new HashMap<>();

    private final GameModel gameModel;
    private final MapController mapController;

    private final Map<Country, JPanel> countryPanels = new HashMap<>();
    private final Map<Transport, TransportIconView> transportIcons = new HashMap<>();

    private  Image transportImage;

    public MapView(GameModel gameModel,MapController mapController){
        this.gameModel = gameModel;
        this.mapController = mapController;
        this.setLayout(null);

        initializeCountries();

        loadTransportImage();


        Color oceanColor = new Color(0, 51, 102, 255);

        this.setBackground(oceanColor);

        initializeTransportIcons();
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scaleMap();
            }
        });
        System.out.println("KQWKEKQWKEKQWEKQWKE");

    }

    public void initializeCountries() {
        for (Country country : gameModel.getCountries()) {
            JPanel countryPanel = new JPanel(null);
            countryPanel.setBackground(country.getColor().getColor());
            countryPanel.setBounds(
                    country.getCountryXCoordinate(),
                    country.getCountryYCoordinate(),
                    country.getCountryWidth(),
                    country.getCountryHeight()
            );
            countryPanel.setToolTipText(country.getName());


            JPanel capital = new JPanel();
            capital.setBackground(Color.RED);
            capital.setBounds(
                    country.getCountryCapitalRelativeXCoordinate(),
                    country.getCountryCapitalRelativeYCoordinate(),
                    10,
                    10
            );
            capital.setToolTipText(country.getCapital());
            countryPanel.add(capital);


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
        System.out.println("Country click!");
        gameModel.getCountries().forEach(country -> country.setSelected(false));

        selectedCountry.setSelected(true);

        mapController.handleMapClick(selectedCountry.getCountryPosition());
        revalidate();
        repaint();
    }

    private void scaleMap() {
        double scaleX = (double) getWidth() / GameSettings.mapWidth;
        double scaleY = (double) getHeight() / GameSettings.windowHeight;

        // Scale countries
        for (Map.Entry<Country, JPanel> entry : countryPanels.entrySet()) {
            Country country = entry.getKey();
            JPanel countryPanel = entry.getValue();

            if (countryPanel != null) {
                int scaledX = (int) (country.getCountryXCoordinate() * scaleX);
                int scaledY = (int) (country.getCountryYCoordinate() * scaleY);
                int scaledWidth = (int) (country.getCountryWidth() * scaleX);
                int scaledHeight = (int) (country.getCountryHeight() * scaleY);

                countryPanel.setBounds(scaledX, scaledY, scaledWidth, scaledHeight);

                // Scale capital position
                Component[] components = countryPanel.getComponents();
                for (Component component : components) {
                    if (component instanceof JPanel) { // Assuming the capital is added as a JPanel
                        JPanel capital = (JPanel) component;
                        int capitalX = (int) (country.getCountryCapitalRelativeXCoordinate() * scaleX);
                        int capitalY = (int) (country.getCountryCapitalRelativeYCoordinate() * scaleY);
                        capital.setBounds(capitalX, capitalY, 10, 10); // Maintain fixed size for the capital
                    }
                }
            }
        }

        for (TransportIconView transportIcon : transportIcons.values()) {
            transportIcon.scale(scaleX, scaleY);
        }

        revalidate();
        repaint();
    }




    private void loadTransportImage() {
        try {
            transportImage = ImageIO.read(getClass().getResource("/plane.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//
private void initializeTransportIcons() {
    for (Transport transport : gameModel.getTransports()) {

        TransportIconView transportIcon = new TransportIconView(transport, transport.getTransportImage(), 3000); // 3 sekundy na przelot
        this.add(transportIcon);
        transportIcons.put(transport, transportIcon);

        this.setComponentZOrder(transportIcon, 0);

        transportIcon.startAnimation();
    }
}

    @Override
    public void onTransportStateUpdate(Country country,TransportType transportType, boolean isEnabled) {
        SwingUtilities.invokeLater(() -> {
            for (Map.Entry<Transport, TransportIconView> entry : transportIcons.entrySet()) {
                Transport transport = entry.getKey();
                TransportIconView iconView = entry.getValue();


                if (transport.getTransportType() == transportType &&
                        (transport.getFromCountry().getName().equals(country.getName()) ||
                                transport.getToCountry().getName().equals(country.getName()))) {
                    iconView.setVisible(isEnabled && transport.isEnabled());
                }
            }
        });
    }
    @Override
    public void onSelectedCountryUpdate(String countryName, double countryPoints, int population, int suspectible, int infected, int cured, int dead, double infectedRate, double recoveryRestinatce, double moratyliRate) {

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
}
