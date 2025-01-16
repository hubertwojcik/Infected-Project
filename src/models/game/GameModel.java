package models.game;

import enums.TransportType;
import game.GameDataInitializer;
import game.GameSettings;
import game.GameSimulationManager;
import models.Transport.Transport;
import models.country.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public GameModel(){
        initializeGameData();
    }


    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }


    public List<Country> getCountries(){
        return countries;
    }

    public int getDayCounter() {
        return dayCounter;
    }

    public Country getSelectedCountry() {
        return selectedCountry;
    }

    public int getTotalCured() {
        return totalCured;
    }

    public int getTotalDead() {
        return totalDead;
    }

    public List<Transport> getTransports() {
        return transports;
    }


    public void setSelectedCountry(Country selectedCountry) {
        this.selectedCountry = selectedCountry;
        notifySelectedCountryUpdate();
    }


    public void disableTransport(Country country, TransportType transportType){
        System.out.println("Wylacz " + transportType + " w " + country.getName());
        for(Transport transport : transports){
            if ((transport.getFromCountry().getName().equals(country.getName()) ||
                    transport.getToCountry().getName().equals(country.getName())) &&
                    transport.getTransportType() == transportType) {
                System.out.println(transport.getFromCountry().getName() + " to " + transport.getToCountry().getName());
                transport.setEnabled(false);
                notifyTransportUpdate(country,transportType, false);
            }
        }
    }

    private void notifyTransportUpdate(Country country,TransportType transportType, boolean isEnabled){
        for (GameObserver gameObserver : observers){
            gameObserver.onTransportStateUpdate(country,transportType, isEnabled);
        }
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

    public void initializeGameData() {
        dayCounter = 0;
        countries = GameDataInitializer.initializeCountries(this);
        transports = GameDataInitializer.initializeTransports(countries);

        virus = GameDataInitializer.initializeVirus(GameSettings.getDifficultyLevel());

        for(Country c : countries){
            if(c.getName().equals("Chiny")){
                c.setVirus(virus,1);
            }
        }

        simulationManager = new GameSimulationManager(countries, transports);
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



    private void checkEndGameConditions(){
            boolean allCountriesWonWithVirus = countries.stream().allMatch(c-> {
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



}
