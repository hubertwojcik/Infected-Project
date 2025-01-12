package views.game;

import models.Transport.Transport;

import javax.swing.*;
import java.awt.*;

public class TransportView extends JPanel {
    private final Transport transport;
    private int positionX;
    private int positionY;
    private Image transportImage;


    public TransportView(Transport transport, Image transportImage) {
        this.transport = transport;
        this.transportImage = transportImage;
        this.positionX = transport.getFromCountry().getMapObjectX();
        this.positionY = transport.getFromCountry().getMapObjectY();
        this.setLayout(null);
        setOpaque(false);

        this.setBounds(positionX,positionY,30,30);
    }

    public void animate() {
        int targetX = transport.getToCountry().getMapObjectX();
        int targetY = transport.getToCountry().getMapObjectY();

        int dx = targetX - positionX;
        int dy = targetY - positionY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 1) {
            positionX += dx / distance;
            positionY += dy / distance;
            setBounds(positionX, positionY, 30, 30);
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Rysowanie obrazu samolotu w obrębie JPanel
        if (transportImage != null) {
            g.drawImage(transportImage, 0, 0, 30, 30, null);
        }
    }



}