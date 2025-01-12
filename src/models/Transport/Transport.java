package models.Transport;

import enums.TransportType;
import models.map.Country;
import java.util.ArrayList;
import java.util.List;

public class Transport  {
    private final Country fromCountry;
    private final Country toCountry;
    private final int dailyFlow;
    private final TransportType transportType;


    private final List<TransportObserver> observers = new ArrayList<>();
    private int positionX;
    private int positionY;
    private boolean isEnabled;

    public Transport(Country fromCountry, Country toCountry, int dailyFlow, TransportType transportType) {
        this.fromCountry = fromCountry;
        this.toCountry = toCountry;
        this.dailyFlow = dailyFlow;
        this.transportType = transportType;

        this.isEnabled = true;


        this.positionX = fromCountry.getMapObjectX();
        this.positionY = fromCountry.getMapObjectY();
        this.isEnabled = true;

    }

    public void addObserver(TransportObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(TransportObserver observer) {
        observers.remove(observer);
    }

    public void executeTransport() {
        synchronized (fromCountry) {
            synchronized (toCountry) {
                int flow = Math.min(dailyFlow, fromCountry.getPopulation());
                fromCountry.adjustPopulation(-flow);
                toCountry.adjustPopulation(flow);

                int infectedFlow = Math.min(flow, fromCountry.getInfected());
                fromCountry.adjustInfected(-infectedFlow);
                toCountry.adjustInfected(infectedFlow);
            }
        }
    }

    public void animate() {
        int targetX = toCountry.getMapObjectX();
        int targetY = toCountry.getMapObjectY();

        while (isEnabled && (positionX != targetX || positionY != targetY)) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            int dx = targetX - positionX;
            int dy = targetY - positionY;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance > 1) {
                positionX += dx / distance;
                positionY += dy / distance;
                notifyObservers();
            } else {
                positionX = targetX;
                positionY = targetY;
                notifyObservers();
                break;
            }
        }
    }

    private void notifyObservers() {
        for (TransportObserver observer : observers) {
            observer.onTransportPositionUpdate(this, positionX, positionY);
        }
    }

    public Country getToCountry() {
        return toCountry;
    }

    public Country getFromCountry() {
        return fromCountry;
    }
}
