package views.map;

import game.GameSettings;
import controllers.map.MapController;
import models.Transport.Transport;
import models.map.Country;
import models.game.GameModel;
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

public class MapView extends JPanel {
    private final GameModel gameModel;
    private final MapController mapController;

    private final Map<Country, JPanel> countryPanels = new HashMap<>();
    private final Map<Transport, TransportIconView> transportIcons = new HashMap<>();

    private Image transportImage;

    public MapView(GameModel gameModel,MapController mapController){
        this.gameModel = gameModel;
        this.mapController = mapController;
        this.setLayout(null);

        initializeCountries();

        loadTransportImage();


        Color oceanColor = new Color(0, 51, 102, 255); // Alpha 255 = pełna nieprzezroczystość

        this.setBackground(oceanColor);


        this.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                scaleMap();
            }
        });

        initializeTransportIcons();

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
        TransportIconView transportIcon = new TransportIconView(transport, transportImage, 3000); // 3 sekundy na przelot
        this.add(transportIcon);
        transportIcons.put(transport, transportIcon);

        // Umieść ikonę transportu na wierzchu
        this.setComponentZOrder(transportIcon, 0);

        // Start animacji
        transportIcon.startAnimation();
    }
}


//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        // Możesz dodać inne elementy graficzne
//    }

}
