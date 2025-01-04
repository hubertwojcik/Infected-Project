package models;

import config.Config;
import controllers.GameController;
import enums.CountryColor;
import enums.TransportType;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private int dayCounter = 0;
    private  List<Country> countries;
    private Country selectedCountry;
    private  List<Transport> transports;

    public GameModel(){
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

    public void updateModel() {
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
        int mapPadding = 25;
        int countrySpacing = 2;

        Country russia = new Country(
                "Rosja",
                144000000,
                mapPadding,
                mapPadding,
                Config.mapWidth - 2 * mapPadding - countrySpacing,
                100 - countrySpacing,
                CountryColor.RUSSIA);
        Country kazakhstan = new Country(
                "Kazakhstan",
                1000000,
                mapPadding,
                russia.getMapObjectY() + russia.getHeight() + countrySpacing,
                200 - countrySpacing,
                150 - countrySpacing,
                CountryColor.KAZAKHSTAN);
        Country mongolia = new Country(
                "Mongolia",
                1000000,
                kazakhstan.getMapObjectX() + kazakhstan.getWidth() + countrySpacing,
                russia.getMapObjectY() + russia.getHeight() + countrySpacing,
                250 - countrySpacing,
                50 - countrySpacing,
                CountryColor.MONGOLIA);
        Country china = new Country("China",
                1400000000,
                mapPadding + kazakhstan.getWidth() + countrySpacing,
                mongolia.getMapObjectY() + mongolia.getHeight() + countrySpacing,
                250 - countrySpacing,
                100 - countrySpacing,
                CountryColor.CHINA);
        Country japan = new Country(
                "Japonia",
                126000000,
                china.getMapObjectX() + china.getWidth() + 50 + countrySpacing,
                150,
                50 - countrySpacing,
                200 - countrySpacing,
                CountryColor.JAPAN);
        Country iran = new Country(
                "Iran",
                126000000,
                mapPadding,
                kazakhstan.getMapObjectY() + kazakhstan.getHeight() + countrySpacing,
                150 - countrySpacing,
                125 - countrySpacing,
                CountryColor.IRAN);
        Country pakistan = new Country(
                "Pakistan",
                126000000,
                iran.getMapObjectX() + iran.getWidth() + countrySpacing,
                kazakhstan.getMapObjectY() + kazakhstan.getHeight() + countrySpacing,
                50 - countrySpacing,
                125 - countrySpacing,
                CountryColor.MALAYSIA);
        Country india = new Country(
                "Indie",
                1300000000,
                pakistan.getMapObjectX() + pakistan.getWidth() + countrySpacing,
                kazakhstan.getMapObjectY() + kazakhstan.getHeight() + countrySpacing,
                150 - countrySpacing,
                200 - countrySpacing,
                CountryColor.INDIA);
        Country vietnam = new Country(
                "Wietnam",
                126000000,
                india.getMapObjectX() + india.getWidth() + countrySpacing,
                kazakhstan.getMapObjectY() + kazakhstan.getHeight() + countrySpacing,
                50 - countrySpacing,
                125 - countrySpacing,
                CountryColor.VIETNAM);
        Country indonesia = new Country(
                "Indonesia",
                126000000,
                300,
                india.getMapObjectY() + india.getHeight() + 25 + countrySpacing,
                250 - countrySpacing,
                50 - countrySpacing,
                CountryColor.INDONESIA);

        Virus virus = new Virus("COVID-19", 0.1, 0.01, 0.002, 14);
        china.setVirus(virus, 1);

        countries.add(russia);
        countries.add(china);
        countries.add(kazakhstan);
        countries.add(mongolia);
        countries.add(india);
        countries.add(japan);
        countries.add(iran);
        countries.add(pakistan);
        countries.add(indonesia);
        countries.add(vietnam);
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
