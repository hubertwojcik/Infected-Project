package models.game;

import enums.TransportType;
import util.GameDataInitializer;
import util.GameSettings;
import util.GameLogicSynchronizer;
import interfaces.GameObserver;
import models.Transport.Transport;
import models.country.Country;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private final List<GameObserver> observers = new ArrayList<>();
    private GameLogicSynchronizer simulationManager;
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
        updateSelectedCountryInformation();
    }


    public void disableTransport(Country country, TransportType transportType){
        for(Transport transport : transports){
            if ((transport.getFromCountry().getName().equals(country.getName()) ||
                    transport.getToCountry().getName().equals(country.getName())) &&
                    transport.getTransportType() == transportType) {
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
            simulationManager.simulateGameLogic();
            dayCounter++;
            updateGlobalStats();
            updateDayCount();
            notifyGlobalStatsUpdate();
            if (selectedCountry != null) {
                updateSelectedCountryData();
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

        simulationManager = new GameLogicSynchronizer(countries, transports);
    }

    private void updateGlobalStats() {
        int tempInfected = 0;
        int tempCured = 0;
        int tempDead = 0;

        for (Country c : countries) {
            tempInfected += c.getInfected();
            tempCured += c.getRecovered();
            tempDead += c.getDead();
        }

        totalInfected = Math.max(0, tempInfected);
        totalCured = Math.max(0, tempCured);
        totalDead = Math.max(0, tempDead);
    }

    private void updateDayCount() {
        for (GameObserver observer : observers) {
            observer.onDayUpdate(dayCounter);
        }
    }

    private void notifyGlobalStatsUpdate() {
        for (GameObserver observer : observers) {
            observer.onGlobalStatsUpdate(totalInfected, totalCured, totalDead);
        }
    }

    private void updateSelectedCountryData() {
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

    private void updateSelectedCountryInformation() {
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

    private void endGame(){
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
                endGame();
            }
    }



}
