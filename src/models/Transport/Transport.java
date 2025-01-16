package models.Transport;

import enums.TransportType;
import models.PeopleTransport;
import models.game.Virus;
import models.country.Country;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Transport  {
    private final Country fromCountry;
    private final Country toCountry;
    private final int dailyFlow;
    private final TransportType transportType;
    private boolean isEnabled;
    private final List<TransportOberver> observers = new ArrayList<>();

    public Transport(Country fromCountry, Country toCountry, int dailyFlow, TransportType transportType) {
        this.fromCountry = fromCountry;
        this.toCountry = toCountry;
        this.dailyFlow = dailyFlow;
        this.transportType = transportType;
        this.isEnabled = true;

    }

    public void addObserver(TransportOberver observer) {
        observers.add(observer);
    }

    public void removeObserver(TransportOberver observer) {
        observers.remove(observer);
    }


    private void notifyObservers() {
        for (TransportOberver observer : observers) {
            observer.onTransportStateChange(this);
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

    public void setEnabled(boolean value){
        this.isEnabled =  value;
        notifyObservers();

    }



    public void executeTransport() {
        synchronized (fromCountry) {
            synchronized (toCountry) {
                doTransportLogic();
            }
        }
    }

    /**
     * Wersja “bezpieczna” (chroniąca przed deadlockiem w razie transportów “krzyżowych”),
     * blokuje kraje w kolejności alfabetycznej (lub np. po hashCode).
     * Możesz używać tego zamiast executeTransport(), jeżeli w Twojej grze
     * zdarzają się wielowątkowe wywołania transportów.
     */
    public void executeTransportSafely() {
        if(!isEnabled) return;
        // Prosty sposób: porównujemy nazwy krajów i decydujemy, w jakiej kolejności je blokować
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
                // Ale logicznie transport przenosi z from -> to,
                // więc w metodzie doTransportLogic i tak musimy
                // wiedzieć, że to from -> to, a nie odwrotnie.
                doTransportLogic();
            }
        }
    }

    /**
     * Właściwa logika transportu (przeniesienie ludzi + ewentualny wirus)
     */
    private void doTransportLogic() {
        //PODROZOWAC MOGA TYLKO OSOBY ZDROWE LUB WYZDROWIALE
        int peopleAllowedToTravel = fromCountry.getSusceptible() + fromCountry.getRecovered();

        int flow = Math.min(dailyFlow, peopleAllowedToTravel);

        if (flow <= 0) return;
        double recoveredRate = (double) fromCountry.getRecovered() / (double) fromCountry.getPopulation();
        int recoveredInTransport = (int) Math.round(flow * recoveredRate);
        int subseptibleIntransport = flow - recoveredInTransport;
//        System.out.println("FLOW WYNOSI =  "  + flow + " | "  + fromCountry.getName());
        PeopleTransport pt = new PeopleTransport(subseptibleIntransport, recoveredInTransport);

        fromCountry.handleImmigrationAndEmigration(pt, false);
        toCountry.handleImmigrationAndEmigration(pt, true);

//        System.out.println("TO Country: " + toCountry.getName() + " | " + pt.recoveredPeople + pt.susceptiblePeople);

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
                // Tworzymy nowe ognisko w kraju docelowym
                toCountry.setVirus(newVirusCopy, 1);

                // Opcjonalnie: komunikat dla użytkownika o nowym ognisku
                System.out.println("Nowe ognisko wirusa w kraju: " + toCountry.getName());
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
