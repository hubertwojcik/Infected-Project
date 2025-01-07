package models.game;

import enums.DifficultyLevel;
import game.GameDataInitializer;
import game.GameSettings;
import game.GameSimulationManager;
import models.map.Country;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    //OBSERVERS
    private final List<GameObserver> observers = new ArrayList<>();
    private GameSimulationManager simulationManager;

    //GAME DATA
    private int totalInfected;
    private int totalCured;
    private int totalDead;

    private int dayCounter = 0;
    private  List<Country> countries;
    private Country selectedCountry;
    private  List<Transport> transports;
    private Virus virus;
    private DifficultyLevel difficultyLevel;


    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    public GameModel(DifficultyLevel difficultyLevel){
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
        notifySelectedCountryUpdate();
    }

    public List<Country> getCountries(){
        return countries;
    }

//    public void updateModel() {
//        runCountries();
//        runTransports();
//        dayCounter++;
//        updateGlobalStats();
//
//        notifyDayUpdate();
//        notifyGlobalStatsUpdate();
//
//
//        if(selectedCountry != null){
//            notifySelectedCountryStatsUpdate();
//        }
//
//    }


    public void updateModel() {
        simulationManager.runSimulationStep();
        dayCounter++;
        updateGlobalStats();
        notifyDayUpdate();
        notifyGlobalStatsUpdate();
        if (selectedCountry != null) {
            notifySelectedCountryStatsUpdate();
        }
    }

    public void startNewGame(){
        initializeGameData();

    }

    private void updateGlobalStats() {
        totalInfected = countries.stream().mapToInt(Country::getInfected).sum();
        totalCured = countries.stream().mapToInt(Country::getRecovered).sum();
        totalDead = countries.stream().mapToInt(Country::getDead).sum();
    }

    private void notifyDayUpdate() {
        for (GameObserver observer : observers) {
            observer.onDayUpdate(dayCounter);
        }
    }

    private void notifyGlobalStatsUpdate() {
        for (GameObserver observer : observers) {
            observer.onGlobalStatsUpdate(totalInfected, totalCured, totalDead);
        }
    }

    private void notifySelectedCountryStatsUpdate() {
        if (selectedCountry != null) {
            for (GameObserver observer : observers) {
                observer.onSelectedCountryUpdate(
                        selectedCountry.getName(),
                        selectedCountry.getPopulation(),
                        selectedCountry.getInfected(),
                        selectedCountry.getRecovered(),
                        selectedCountry.getDead()
                );
            }
        }
    }

    private void notifySelectedCountryUpdate() {
        for (GameObserver observer : observers) {
            Country selectedCountry = this.selectedCountry;
            System.out.println("SELECTED COUNTRY: " + selectedCountry.getName());
            if (selectedCountry != null) {
                observer.onSelectedCountryUpdate(
                        selectedCountry.getName(),
                        selectedCountry.getPopulation(),
                        selectedCountry.getInfected(),
                        selectedCountry.getRecovered(),
                        selectedCountry.getDead()
                );
            }
        }
    }

    public void initializeGameData() {
        System.out.println("GameSettings.getDifficultyLevel()"+GameSettings.getDifficultyLevel());
        dayCounter = 0;
        countries = GameDataInitializer.initializeCountries();
        transports = GameDataInitializer.initializeTransports(countries);


        virus = GameDataInitializer.initializeVirus(GameSettings.getDifficultyLevel());

        for(Country c : countries){
            if(c.getName().equals("Chiny")){
                c.setVirus(virus,1);
            }
        }

        simulationManager = new GameSimulationManager(countries, transports);

//        System.out.println("POZIOM TRUNOSC: " + GameSettings.getDifficultyLevel());
    }

    private void runCountries() {
        List<Thread> countryThreads = new ArrayList<>();

        for (Country country : countries) {
            Thread thread = new Thread(() -> {
                try {
                    country.simulateInfectionSpread();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            countryThreads.add(thread);
            thread.start();
        }

        for (Thread thread : countryThreads) {
            try {
                thread.join(); // Czekaj na zakończenie wątku
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Wątek synchronizacji kraju został przerwany: " + e.getMessage());
            }
        }
    }

    private void runTransports() {
        List<Thread> transportThreads = new ArrayList<>();

        for (Transport transport : transports) {
            Thread thread = new Thread(() -> {
                try {
                    transport.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            transportThreads.add(thread);
            thread.start();
        }

        for (Thread thread : transportThreads) {
            try {
                thread.join(); // Czekaj na zakończenie wątku
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Wątek synchronizacji transportu został przerwany: " + e.getMessage());
            }
        }
    }




}
