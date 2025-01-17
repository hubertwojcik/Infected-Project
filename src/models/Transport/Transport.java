package models.Transport;

import enums.TransportType;
import models.game.Virus;
import models.country.Country;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Transport  {
    private final Country fromCountry;
    private final Country toCountry;
    private final int dailyFlow;
    private final TransportType transportType;
    private boolean isEnabled;


    public Transport(Country fromCountry, Country toCountry, int dailyFlow, TransportType transportType) {
        this.fromCountry = fromCountry;
        this.toCountry = toCountry;
        this.dailyFlow = dailyFlow;
        this.transportType = transportType;
        this.isEnabled = true;
    }

    public Country getToCountry() {
        return toCountry;
    }

    public Country getFromCountry() {
        return fromCountry;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean value){
        this.isEnabled =  value;
    }


    public void transportPeoplWithSync() {
        if(!isEnabled) return;

        Country firstLock, secondLock;
        if (fromCountry.getName().compareTo(toCountry.getName()) < 0) {
            firstLock = fromCountry;
            secondLock = toCountry;
        } else {
            firstLock = toCountry;
            secondLock = fromCountry;
        }

        synchronized (firstLock) {
            synchronized (secondLock) {
                transportPeople();
            }
        }
    }

    private void transportPeople() {
        int peopleAllowedToTravel = fromCountry.getSusceptible() + fromCountry.getRecovered();

        int flow = Math.min(dailyFlow, peopleAllowedToTravel);

        if (flow <= 0) return;
        double recoveredRate = (double) fromCountry.getRecovered() / (double) fromCountry.getPopulation();
        int recoveredInTransport = (int) Math.round(flow * recoveredRate);
        int subseptibleIntransport = flow - recoveredInTransport;
        PeopleTransport pt = new PeopleTransport(subseptibleIntransport, recoveredInTransport);

        fromCountry.handleImmigrationAndEmigration(pt, false);
        toCountry.handleImmigrationAndEmigration(pt, true);



        double infectedRatio = (double) fromCountry.getInfected() / fromCountry.getPopulation();
        double probNoOneIsExposed = Math.pow((1.0 - infectedRatio), flow);
        double probAtLeastOneIsExposed = 1.0 - probNoOneIsExposed;

        boolean virusIsTransferred = (Math.random() < probAtLeastOneIsExposed);
        if (virusIsTransferred) {
            if (toCountry.getVirus() == null) {
                Virus newVirusCopy = new Virus(
                        fromCountry.getVirus().getName(),
                        fromCountry.getVirus().getInfectionRate(),
                        fromCountry.getVirus().getRecoveryRate(),
                        fromCountry.getVirus().getMortalityRate(),
                        fromCountry.getVirus().getIncubationPeriod()
                );

                toCountry.setVirus(newVirusCopy, 1);


                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(
                            null,
                            "Nowe ognisko wirusa wykryte w kraju: " + toCountry.getName(),
                            "Nowe ognisko",
                            JOptionPane.WARNING_MESSAGE
                    );
                });
            }
        }
    }


    public Image getTransportImage() {
        try {
            switch (transportType){
                case AIR:
                    return ImageIO.read(getClass().getResource("/plane.png"));

                case RAILWAY:
                    return ImageIO.read(getClass().getResource("/train.png"));

                case WATER:
                    return ImageIO.read(getClass().getResource("/ship.png"));
                default:
                    return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
