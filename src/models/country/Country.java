package models.country;

import java.util.*;
import java.util.List;


import enums.DifficultyLevel;
import game.GameSettings;
import interfaces.Upgrade;
import models.PeopleTransport;
import models.game.Virus;
import enums.CountryColor;

public class Country extends CountryCoordinates {
    private String name;
    private final String capital;
    private int population;
    private boolean isSelected;
    int infected = 0;
    int recovered = 0;
    int dead = 0;
    private int susceptible;
    private int dayCounter = 0;
    private final Map<Integer, Integer> infectedMap = new HashMap<>();
    private Random random = new Random();
    private final CountryColor color;
    // VIRUS
    private Virus virus;
    private double infectionRate;
    private double recoveryRate;

    private double mortalityRate;
    //
    private final Object lock = new Object();
    //

    //UPGRADES
    private final Map<Upgrade, Boolean> upgrades = new HashMap<>();
    private double countryPoints;


    public Country(String name, int population, int mapX, int mapY, int width, int height,CountryColor color,String capital, int capitalX,int capitalY) {
        super(mapX, mapY, width, height, capitalX + mapX, capitalY + mapY, capitalX, capitalY);
        this.name = name;
        this.capital = capital;
        this.population = population;
        this.susceptible = population;
        this.infected = 0;
        this.recovered = 0;
        this.dead = 0;
        this.color = color;
    }

    public void initializeUpgrades(List<Upgrade> upgradesList) {
        for (Upgrade upgrade : upgradesList) {
            upgrades.put(upgrade, false);
        }
    }

    public boolean isUpgradeAvailable(Upgrade upgrade){
        return upgrades.getOrDefault(upgrade,false);
    }

    public CountryColor getColor() {
        return color;
    }


    public Map<Upgrade, Boolean> getUpgrades() {
        return upgrades;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getRecovered() {
        return recovered;
    }

    public int getDead() {
        return dead;
    }


    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public int getPopulation() {
        synchronized (lock) {
            return population;
        }
    }

    public String getCapital() {
        return capital;
    }



    // VIRUS LOGIC
    public synchronized void simulateInfectionSpread() {
//DOSTOWAC SAM KONIEC< ZEBY NIE ZOSTAWAL 1!!!!!
        if (virus == null || population <= 0) return;

        double potentialInfections = infectionRate * susceptible * infected / population;
        int newInfections = (int) Math.max(1, Math.min(Math.ceil(potentialInfections), susceptible));
        System.out.println("NAMe: "+name+" | New infections: "+newInfections);
        int recoverOrDeadDay = dayCounter + getRandomDays(14, 21);
        infectedMap.put(recoverOrDeadDay, infectedMap.getOrDefault(recoverOrDeadDay, 0) + newInfections);

        susceptible -= newInfections;
        infected += newInfections;

        int todayInfected = infectedMap.getOrDefault(dayCounter, 0);
        if (todayInfected > 0) {
            int recoveriesToday = (int) (todayInfected * recoveryRate);
            int deathsToday = todayInfected - recoveriesToday;
            System.out.println("recoveryRate: " + recoveryRate);
            infected -= todayInfected;
            recovered += recoveriesToday;
            dead += deathsToday;

            infectedMap.remove(dayCounter);
            adjustCountryPoints(recoveriesToday);
        }

        susceptible = Math.max(0, susceptible);
        infected = Math.max(0, infected);
        recovered = Math.max(0, recovered);
        dead = Math.max(0, dead);

        System.out.println(
                "Day: " + dayCounter +
                        " | Susceptible: " + susceptible +
                        " | Infected: " + infected +
                        " | Recovered: " + recovered +
                        " | Dead: " + dead +
                        " | Recovery Map: " + infectedMap
        );

        dayCounter++;
    }


    private void adjustCountryPoints(int recoveredToday){
        double points = (((double) recoveredToday) / population) * 100;
        double multiplier = GameSettings.getDifficultyLevel().getScoreModifier();
        double newPoints = points * multiplier;

        double roundedPoints = Math.round(newPoints * 100.0) / 100.0;

        this.countryPoints += roundedPoints;

    }


    public void adjustPopulation(PeopleTransport payload, boolean isTransportToCountry) {
        synchronized (lock) {
            if(isTransportToCountry){
                this.infected += payload.infectedPeople;
                this.population += payload.healthyPeople + payload.infectedPeople;
            }else{
                this.infected -= payload.infectedPeople;
                this.population -= payload.healthyPeople + payload.infectedPeople;
            }
        }
    }

    public void applyUpgrade(Upgrade upgrade, String effectKey, double effectValue ) {
        switch (effectKey) {
            case "Zaraźliwość":
                modifyInfectionRate(effectValue);
                break;
            case "Zdrowienie":
                modifyRecoveryRate(effectValue);
                break;
            case "Śmiertelność":
                modifyMortalityRate(effectValue);
                break;
            default:
                throw new IllegalArgumentException("Nieznany efekt: " + effectKey);
        }

        upgrades.put(upgrade, true);

    }
    public void modifyInfectionRate(double modifier){
        this.infectionRate += modifier;
        this.infectionRate = Math.max(0, this.infectionRate);
        System.out.println("DASDASD" + this.infectionRate);
    }

    public void modifyRecoveryRate(double modifier){
        this.recoveryRate += modifier;
        this.recoveryRate = Math.min(1, this.recoveryRate);
    }

    public void modifyMortalityRate(double modifier){
        this.mortalityRate += modifier;
        this.mortalityRate = Math.max(0, this.mortalityRate);
    }


    public int getInfected() {
        synchronized (lock) {
            return infected;
        }
    }


    public double getMortalityRate() {
        return mortalityRate;
    }

    //TODO
    public double getRecoverRate() {
        return recoveryRate;
    }

    public double getInfectionRate() {
        return infectionRate;
    }

    public Virus getVirus() {
        return virus;
    }


    // Helpers
    private int getRandomDays(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public void setVirus(Virus virus, int infectedPersons) {
        if (this.virus == null && virus != null) {
            this.virus = virus;
            this.infected = infectedPersons;
            this.infectionRate = virus.getInfectionRate();
            this.recoveryRate = virus.getRecoveryRate();
            this.mortalityRate = virus.getMortalityRate();
        };

    }

    public double getCountryPoints() {
        return countryPoints;
    }
}
