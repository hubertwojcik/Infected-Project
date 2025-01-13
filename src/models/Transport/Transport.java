package models.Transport;

import enums.TransportType;
import models.PeopleTransport;
import models.game.Virus;
import models.map.Country;

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
    private Image transportImage;

    public Transport(Country fromCountry, Country toCountry, int dailyFlow, TransportType transportType) {
        this.fromCountry = fromCountry;
        this.toCountry = toCountry;
        this.dailyFlow = dailyFlow;
        this.transportType = transportType;
        this.isEnabled = true;

    }

    public void executeTransport() {
        synchronized (fromCountry) {
            synchronized (toCountry) {
                int flow = Math.min(dailyFlow, fromCountry.getPopulation());

                double countryInfectedRate = ((double) fromCountry.getInfected()) / ((double )fromCountry.getPopulation());

                int infectedPeopleIntransport =  (int ) (flow * countryInfectedRate);
                int healthyPeopleInTransport = flow - infectedPeopleIntransport;

                if(toCountry.getInfected() == 0 && infectedPeopleIntransport > 0 && toCountry.getVirus() == null){
                    PeopleTransport fromCountryPayload = new PeopleTransport(infectedPeopleIntransport,healthyPeopleInTransport);
                    fromCountry.adjustPopulation(fromCountryPayload,false);

                    Virus virus = new Virus(
                            fromCountry.getVirus().getName(),
                            fromCountry.getVirus().getInfectionRate(),
                            fromCountry.getVirus().getRecoveryResistance(),
                            fromCountry.getVirus().getMortalityRate(),
                            fromCountry.getVirus().getIncubationPeriod()
                            );
                    toCountry.setVirus(virus, fromCountryPayload.infectedPeople);

                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(
                                null,
                                "Nowe ognisko wirusa wykryte w kraju: " + toCountry.getName(),
                                "Nowe ognisko",
                                JOptionPane.WARNING_MESSAGE
                        );
                    });

                    PeopleTransport toCountryPayload = new PeopleTransport(0,healthyPeopleInTransport);
                    toCountry.adjustPopulation(toCountryPayload,true);

                }else{
                    PeopleTransport fromCountryPayload = new PeopleTransport(infectedPeopleIntransport,healthyPeopleInTransport);
                    fromCountry.adjustPopulation(fromCountryPayload,false);
                    toCountry.adjustPopulation(fromCountryPayload,true);
                }

            }
        }
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
