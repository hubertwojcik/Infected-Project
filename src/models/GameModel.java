package models;

import controllers.GameController;
import enums.TransportType;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    GameController gameController;
    private int dayCounter = 0;
    // COUNTRIES
    private  List<Country> countries;
    private Country selectedCountry;
    //Trasport
    private  List<Transport> transports;

    public GameModel(GameController gameController){
        this.gameController= gameController;
        initializeGameData();
    }

    public int getDayCounter() {
        return dayCounter;
    }

    public Country getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(Country selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

    public List<Country> getCountries(){
        return countries;
    }

    public void updateGameState() {
            synchronizeCountries();
            synchronizeTransports();
            dayCounter++;
    }

    public void initializeGameData() {
        dayCounter = 0;
        countries = new ArrayList<>();
        transports = new ArrayList<>();
        initializeCountries();
        initializeTransports();
    }

    private void synchronizeCountries() {
        List<Thread> countryThreads = new ArrayList<>();

        for (Country country : countries) {
            Thread thread = new Thread(country::simulateInfectionSpread);
            countryThreads.add(thread);
            thread.start();
        }


        for (Thread thread : countryThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Wątek synchronizacji kraju został przerwany: " + e.getMessage());
            }
        }
    }

    private void synchronizeTransports() {
        List<Thread> transportThreads = new ArrayList<>();

        for (Transport transport : transports) {
            Thread thread = new Thread(transport::run);
            transportThreads.add(thread);
            thread.start();
        }


        for (Thread thread : transportThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Wątek synchronizacji transportu został przerwany: " + e.getMessage());
            }
        }
    }

    private void initializeCountries() {
        Country russia = new Country("Russia", 144000000, 50, 50, 200, 100);
        Country china = new Country("China", 1400000000, 300, 150, 150, 100);
        china.setVirus(new Virus("COVID-19", 0.4, 0.01, 0.002, 14), 1);

        countries.add(russia);
        countries.add(china);
        countries.add(new Country("India", 1300000000, 300, 300, 100, 100));
        countries.add(new Country("Japan", 126000000, 500, 200, 100, 50));
    }

    private void initializeTransports() {
        Country russia = countries.stream().filter(c -> c.getName().equals("Russia")).findFirst().orElse(null);
        Country china = countries.stream().filter(c -> c.getName().equals("China")).findFirst().orElse(null);

        if (russia != null && china != null) {
            transports.add(new Transport(russia, china, 1000, TransportType.RAILWAY));
            transports.add(new Transport(china, russia, 2000, TransportType.AIR));
            //            rusChina.enable();
//            chinaRus.enable();
        }
    }




}
