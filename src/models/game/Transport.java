package models.game;

import enums.TransportType;
import models.map.Country;



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
        this.isEnabled=true;
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

    public boolean isEnabled() {
        return isEnabled;
    }

}