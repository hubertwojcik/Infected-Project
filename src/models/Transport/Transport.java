package models.Transport;

import enums.TransportType;
import models.PeopleTransport;
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

//
//        // 1) Obliczamy odsetek zakażonych i wyleczonych w kraju źródłowym
//        double infectedRate  = (double) fromCountry.getInfected()  / (double) fromCountry.getPopulation();
//        double recoveredRate = (double) fromCountry.getRecovered() / (double) fromCountry.getPopulation();
//
//        // 2) Liczymy liczbę zainfekowanych i wyleczonych w transporcie
//        int infectedPeopleInTransport  = (int) Math.round(flow * infectedRate);
//        int infectedPeople = Math.min(fromCountry)
//        int recoveredInTransport       = (int) Math.round(flow * recoveredRate);
//
//        // 3) Reszta to zdrowi (podatni)
//        int healthyPeopleInTransport = flow - infectedPeopleInTransport - recoveredInTransport;
//
//        // W razie błędów zaokrągleń: korygujemy do zera i zmniejszamy recoveredInTransport
//        if (healthyPeopleInTransport < 0) {
//            healthyPeopleInTransport = 0;
//            int usedSlots = infectedPeopleInTransport;
//            // Tyle "zajęli" zainfekowani
//            int leftForRecovered = flow - usedSlots;
//            if (leftForRecovered < 0) {
//                // W skrajnym wypadku coś się "wysypało" – wtedy zero wyleczonych
//                leftForRecovered = 0;
//            }
//            recoveredInTransport = Math.min(recoveredInTransport, leftForRecovered);
//        }
//
//        // 4) Zdejmujemy tych ludzi z kraju źródłowego
//        PeopleTransport fromCountryPayload = new PeopleTransport(
//                infectedPeopleInTransport,
//                healthyPeopleInTransport,
//                recoveredInTransport
//        );
//        fromCountry.adjustPopulation(fromCountryPayload, false);
//
//        // 5) Jeśli w kraju docelowym nie było wirusa, a teraz go przywozimy:
//        boolean newVirusArrives = (toCountry.getVirus() == null
//                && infectedPeopleInTransport > 0);
//
//        if (newVirusArrives) {
//            // a) Tworzymy kopię wirusa w kraju docelowym
//            Virus newVirusCopy = new Virus(
//                    fromCountry.getVirus().getName(),
//                    fromCountry.getVirus().getInfectionRate(),
//                    fromCountry.getVirus().getRecoveryRate(),
//                    fromCountry.getVirus().getMortalityRate(),
//                    fromCountry.getVirus().getIncubationPeriod()
//            );
//
//            // b) Ustawiamy wirusa z tyloma zainfekowanymi, ilu przyjechało
//            toCountry.setVirus(newVirusCopy, 0);
//
//            // c) Komunikat – "Nowe ognisko!"
//            SwingUtilities.invokeLater(() -> {
//                JOptionPane.showMessageDialog(
//                        null,
//                        "Nowe ognisko wirusa wykryte w kraju: " + toCountry.getName(),
//                        "Nowe ognisko",
//                        JOptionPane.WARNING_MESSAGE
//                );
//            });
//            System.out.println("=======================");
//            System.out.println("=======================");
//            System.out.println("Transfer inf: " + infectedPeopleInTransport + " | " +"Healthy: " + healthyPeopleInTransport + " | " + "Recovered: " + recoveredInTransport);
//            System.out.println("=======================");
//            System.out.println("=======================");
//
//            // d) Do docelowego kraju przenosimy *tylko* healthy i recovered,
//            //    bo infectedPeopleInTransport zostało już doliczone w setVirus(...).
//            PeopleTransport toCountryPayload = new PeopleTransport(
//                    infectedPeopleInTransport,
//                    healthyPeopleInTransport,
//                    recoveredInTransport
//            );
////            toCountry.transferVirus(newVirusCopy, toCountryPayload);
//            toCountry.adjustPopulation(toCountryPayload, true);
//
//        } else {
//            // 6) Normalna sytuacja: przenosimy wszystkich (infected, healthy, recovered)
//            PeopleTransport toCountryPayload = new PeopleTransport(
//                    infectedPeopleInTransport,
//                    healthyPeopleInTransport,
//                    recoveredInTransport
//            );
//            toCountry.adjustPopulation(toCountryPayload, true);
//        }
//    }
//

//    public void executeTransport() {
//        synchronized (fromCountry) {
//            synchronized (toCountry) {
//                int flow = Math.min(dailyFlow, fromCountry.getPopulation());
//
//                double countryInfectedRate = ((double) fromCountry.getInfected()) / ((double )fromCountry.getPopulation());
//
//                int infectedPeopleIntransport =  (int ) (flow * countryInfectedRate);
//                int healthyPeopleInTransport = flow - infectedPeopleIntransport;
//
//                if(toCountry.getInfected() == 0 && infectedPeopleIntransport > 0 && toCountry.getVirus() == null){
//                    PeopleTransport fromCountryPayload = new PeopleTransport(infectedPeopleIntransport,healthyPeopleInTransport);
//                    fromCountry.adjustPopulation(fromCountryPayload,false);
//
//                    Virus virus = new Virus(
//                            fromCountry.getVirus().getName(),
//                            fromCountry.getVirus().getInfectionRate(),
//                            fromCountry.getVirus().getRecoveryRate(),
//                            fromCountry.getVirus().getMortalityRate(),
//                            fromCountry.getVirus().getIncubationPeriod()
//                            );
//                    toCountry.setVirus(virus, fromCountryPayload.infectedPeople);
//
//                    SwingUtilities.invokeLater(() -> {
//                        JOptionPane.showMessageDialog(
//                                null,
//                                "Nowe ognisko wirusa wykryte w kraju: " + toCountry.getName(),
//                                "Nowe ognisko",
//                                JOptionPane.WARNING_MESSAGE
//                        );
//                    });
//
//                    PeopleTransport toCountryPayload = new PeopleTransport(0,healthyPeopleInTransport);
//                    toCountry.adjustPopulation(toCountryPayload,true);
//
//                }else{
//                    PeopleTransport fromCountryPayload = new PeopleTransport(infectedPeopleIntransport,healthyPeopleInTransport);
//                    fromCountry.adjustPopulation(fromCountryPayload,false);
//                    toCountry.adjustPopulation(fromCountryPayload,true);
//                }
//
//            }
//        }
//    }





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
