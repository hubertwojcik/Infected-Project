package views.transport;

import models.Transport.Transport;

import javax.swing.*;
import java.awt.*;

public class TransportIconView extends JLabel {
    private final Transport transport;
    private int positionX;
    private int positionY;

    private final int startX;
    private final int startY;
    private final int targetX;
    private final int targetY;

    private boolean directionToTraget;
    private final int animationDuration; // czas animacji w milisekundach
    private final int frameRate;         // liczba klatek na sekundę
    private final int totalFrames;       // liczba wszystkich klatek
    private int currentFrame;            // obecna klatka

    public TransportIconView(Transport transport, Image transportImage, int animationDuration) {
        this.transport = transport;
        setIcon(new ImageIcon(transportImage));
        setSize(30, 30);
        setOpaque(false);

        this.startX = transport.getFromCountry().getRandomLocationWithinCountry().x;
        this.startY = transport.getFromCountry().getRandomLocationWithinCountry().y;
        this.targetX = transport.getToCountry().getRandomLocationWithinCountry().x;
        this.targetY = transport.getToCountry().getRandomLocationWithinCountry().y;

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
            while (true) { // Zapętlenie
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

    private void resetPosition() {
        positionX = transport.getFromCountry().getMapObjectX();
        positionY = transport.getFromCountry().getMapObjectY();
        currentFrame = 0; // Restart animacji
        SwingUtilities.invokeLater(() -> setLocation(positionX, positionY));
    }

    public void updatePosition(int x, int y) {
        positionX = x;
        positionY = y;
        setLocation(x, y);
    }
}