package models.game;

import game.GameDataInitializer;
import game.GameSettings;
import game.GameSimulationManager;
import models.Transport.Transport;
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


    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

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
        notifySelectedCountryUpdate();
    }

    public List<Country> getCountries(){
        return countries;
    }

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
        int tempInfected = 0;
        int tempCured = 0;
        int tempDead = 0;

        for (Country c : countries){
            tempInfected += c.getInfected();
            tempCured += c.getRecovered();
            tempDead += c.getDead();
        }

        totalInfected = tempInfected;
        totalCured = tempCured;
        totalDead = tempDead;

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
            observer.onSelectedCountryUpdate(
                    selectedCountry.getName(),
                    selectedCountry.getPopulation(),
                    selectedCountry.getInfected(),
                    selectedCountry.getRecovered(),
                    selectedCountry.getDead()
            );
        }
    }

    public void initializeGameData() {
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

    }

    public List<Transport> getTransports() {
        return transports;
    }
}
