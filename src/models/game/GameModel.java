package models.game;

import game.GameDataInitializer;
import game.GameSettings;
import game.GameSimulationManager;
import models.Transport.Transport;
import models.country.Country;

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
    private final Object lock = new Object();


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

    public synchronized void updateModel() {
            simulationManager.runSimulationStep();
            dayCounter++;
            updateGlobalStats();
            notifyDayUpdate();
            notifyGlobalStatsUpdate();
            if (selectedCountry != null) {
                notifySelectedCountryStatsUpdate();
            }

            checkEndGameConditions();
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
                        selectedCountry.getCountryPoints(),
                        selectedCountry.getPopulation(),
                        selectedCountry.getSusceptible(),
                        selectedCountry.getInfected(),
                        selectedCountry.getRecovered(),
                        selectedCountry.getDead(),
                        selectedCountry.getInfectionRate(),
                        selectedCountry.getRecoverRate(),
                        selectedCountry.getMortalityRate()
                );
            }
        }
    }

    private void notifySelectedCountryUpdate() {
        for (GameObserver observer : observers) {
            Country selectedCountry = this.selectedCountry;
            observer.onSelectedCountryUpdate(
                    selectedCountry.getName(),
                    selectedCountry.getCountryPoints(),
                    selectedCountry.getPopulation(),
                    selectedCountry.getSusceptible(),
                    selectedCountry.getInfected(),
                    selectedCountry.getRecovered(),
                    selectedCountry.getDead(),
                    selectedCountry.getInfectionRate(),
                    selectedCountry.getRecoverRate(),
                    selectedCountry.getMortalityRate()
            );
        }
    }

    private void notifyEndGame(){
        for (GameObserver observer : observers) {
            observer.onGameEnd();
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


    private void checkEndGameConditions(){
        System.out.println("SPRAWDZANIE WARUNKU");
            boolean allCountriesWonWithVirus = countries.stream().allMatch(c-> {

                System.out.println("DEFATED:  " + c.getName() + " | " + c.isVirusDefated());
                return c.isVirusDefated();
            });

            boolean allPeopleHealthy = countries.stream().allMatch(c->c.getInfected() < 1 && c.getSusceptible() >= 0
            );

            boolean allDiseasesStopSpreading = countries.stream().allMatch(c->c.getInfectionRate()  == 0

            );

            if(allCountriesWonWithVirus || allDiseasesStopSpreading || allPeopleHealthy){
                notifyEndGame();
            }
    }

    public int getTotalCured() {
        return totalCured;
    }

    public int getTotalDead() {
        return totalDead;
    }
}
