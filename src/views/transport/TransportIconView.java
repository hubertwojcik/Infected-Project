package views.transport;

import models.Transport.Transport;

import javax.swing.*;
import java.awt.*;

public class TransportIconView extends JLabel {
    private final Transport transport;
    private int positionX;
    private int positionY;

    private  int startX;
    private  int startY;
    private  int targetX;
    private  int targetY;

    private boolean directionToTraget;
    private final int animationDuration; // czas animacji w milisekundach
    private final int frameRate;         // liczba klatek na sekundę
    private final int totalFrames;       // liczba wszystkich klatek
    private int currentFrame;            // obecna klatka

    private final int transportIconSize = 20;

    public TransportIconView(Transport transport, Image transportImage, int animationDuration) {
        this.transport = transport;
        setIcon(new ImageIcon(transportImage));
        setSize(transportIconSize, transportIconSize);
        setOpaque(false);

        this.startX = transport.getFromCountry().getCountryCapitalGlobalXCoordinate() - (transportIconSize/  2);
        this.startY = transport.getFromCountry().getCountryCapitalGlobalYCoordinate() - (transportIconSize/  2);
        this.targetX = transport.getToCountry().getCountryCapitalGlobalXCoordinate() - (transportIconSize/  2);
        this.targetY = transport.getToCountry().getCountryCapitalGlobalYCoordinate() - (transportIconSize/  2);

        this.positionX = startX;
        this.positionY = startY;

        this.directionToTraget = true;
        this.animationDuration = animationDuration;
        this.frameRate = 30;
        this.totalFrames = (animationDuration / 1000) * frameRate;
        this.currentFrame = 0;

        updatePosition(positionX, positionY);
    }
    public void startAnimation() {
        new Thread(() -> {
            while (true) {
                double progress = (double) currentFrame / totalFrames; // Procent ukończenia

                // Obliczenie pozycji na podstawie kierunku
                if (directionToTraget) {
                    positionX = (int) (startX + (targetX - startX) * progress);
                    positionY = (int) (startY + (targetY - startY) * progress);
                } else {
                    positionX = (int) (targetX + (startX - targetX) * progress);
                    positionY = (int) (targetY + (startY - targetY) * progress);
                }

                SwingUtilities.invokeLater(() -> setLocation(positionX, positionY));

                currentFrame++;

                if (currentFrame >= totalFrames) {
                    currentFrame = 0; // Reset klatki
                    directionToTraget = !directionToTraget; // Zmiana kierunku
                }

                try {
                    Thread.sleep(1000 / frameRate); // Czas pomiędzy klatkami
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }


    public void scale(double scaleX, double scaleY) {
        // Skaluj pozycje startowe i docelowe
        this.startX = (int) (transport.getFromCountry().getCountryCapitalGlobalXCoordinate() * scaleX) - (transportIconSize / 2);
        this.startY = (int) (transport.getFromCountry().getCountryCapitalGlobalYCoordinate() * scaleY) - (transportIconSize / 2);
        this.targetX = (int) (transport.getToCountry().getCountryCapitalGlobalXCoordinate() * scaleX) - (transportIconSize / 2);
        this.targetY = (int) (transport.getToCountry().getCountryCapitalGlobalYCoordinate() * scaleY) - (transportIconSize / 2);
    }


    public void updatePosition(int x, int y) {
        positionX = x;
        positionY = y;
        setLocation(x, y);
    }
}