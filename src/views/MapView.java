package views;

import controllers.MapController;
import models.AppModel;
import models.GameModel;

import javax.swing.*;
import java.awt.*;

public class MapView extends JPanel {
    private final GameModel gameModel;
    private final MapController mapController;

    public MapView(GameModel gameModel,MapController mapController){
        this.gameModel = gameModel;
        this.mapController = mapController;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        gameModel.getCountries().forEach(country -> {
            g2.setColor(false ? Color.RED : Color.GREEN);
            Rectangle bounds = new Rectangle(country.getMapObjectX(),country.getMapObjectY(), country.getWidth(),country.getHeight());
            g2.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            g2.setColor(Color.BLACK);
            g2.drawString(country.getName(), bounds.x + 5, bounds.y + 15);
        });
    }
}
