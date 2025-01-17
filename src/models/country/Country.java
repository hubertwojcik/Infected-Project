package models.country;

import java.util.*;
import java.util.List;

import enums.TransportType;
import util.GameSettings;
import interfaces.Upgrade;
import models.Transport.PeopleTransport;
import models.game.GameModel;
import models.game.Virus;
import enums.CountryColor;
import util.Helpers;


public class Country extends CountryCoordinates {
    private final GameModel gameModel;
    private final String name;
    private final String capital;
    private double countryPoints;
    private final CountryColor color;
    private final double countryPointsGrow = 0.25;
    private int population;
    private int infected = 0;
    private int recovered = 0;
    private int dead = 0;
    private boolean isSelected;
    private int susceptible;
    private int dayCounter = 0;
    private final Map<Integer, Integer> diseaseResultDayMap = new HashMap<>();
    private Virus virus;
    private double infectionRate;
    private double recoveryRate;
    private double mortalityRate;
    private boolean isVirusDefated = false;
    private final Map<Upgrade, Boolean> upgrades = new HashMap<>();
    private final Object lock = new Object();
    private final Random random = new Random();


    public Country(GameModel gameModel,String name, int population, int mapX, int mapY, int width, int height,CountryColor color,String capital, int capitalX,int capitalY) {
        super(mapX, mapY, width, height, capitalX + mapX, capitalY + mapY, capitalX, capitalY);
        this.gameModel = gameModel;
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


    public CountryColor getColor() {
        return color;
    }


    public Map<Upgrade, Boolean> getUpgrades() {
        return upgrades;
    }

    public String getName() {
        return name;
    }

    public int getRecovered() {
        return recovered;
    }

    public int getDead() {
        return dead;
    }

    public int getPopulation() {
        synchronized (lock) {
            return population;
        }
    }

    public String getCapital() {
        return capital;
    }


    public void setSelected(boolean selected) {
        isSelected = selected;
    }



    public int getInfected() {
        synchronized (lock) {
            return infected;
        }
    }

    public double getMortalityRate() {
        return mortalityRate;
    }

    public double getRecoverRate() {
        return recoveryRate;
    }

    public double getInfectionRate() {
        return infectionRate;
    }

    public Virus getVirus() {
        return virus;
    }

    public double getCountryPoints() {
        return countryPoints;
    }


    public synchronized int getSusceptible() {
        return susceptible;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public  synchronized int getDayCounter() {
        return dayCounter;
    }

    public boolean isVirusDefated() {
        return isVirusDefated;
    }

    public void setVirus(Virus virus, int infectedPersons) {
        if (this.virus == null && virus != null) {
            this.virus = virus;
            this.infected = infectedPersons;
            this.susceptible -= infectedPersons ;
            this.infectionRate = virus.getInfectionRate();
            this.recoveryRate = virus.getRecoveryRate();
            this.mortalityRate = virus.getMortalityRate();
        };

    }

    public synchronized void handleImmigrationAndEmigration(PeopleTransport payload, boolean isTransportToCountry) {
        int recovered = payload.recoveredPeople;
        int susceptible = payload.susceptiblePeople;

        if (isTransportToCountry) {
            this.susceptible = Math.max(0, this.susceptible + susceptible);
            this.population = Math.max(0, this.population + (susceptible + recovered));
            this.recovered = Math.max(0, this.recovered + recovered);
        } else {
            if (this.susceptible < susceptible) {
                susceptible = this.susceptible;
            }
            if (this.recovered < recovered) {
                recovered = this.recovered;
            }
            this.susceptible = Math.max(0, this.susceptible - susceptible);
            this.population = Math.max(0, this.population - (susceptible + recovered));
            this.recovered = Math.max(0, this.recovered - recovered);
        }
    }

    public synchronized void SIRModelDiseaseSpreadSimulation() {
        increaseCountryPoints(countryPointsGrow);

        if (virus == null || population <= 0) return;

        if ((infected == 0 && susceptible == 0 && diseaseResultDayMap.isEmpty()) || isVirusDefated) {
            return;
        }

        int todayInfected = diseaseResultDayMap.getOrDefault(dayCounter, 0);

        if (susceptible < 1) {
            if (todayInfected > 0) {
                int recoveriesToday = Math.max(1, (int) (todayInfected * recoveryRate));
                int deathsToday = todayInfected - recoveriesToday;

                infected = Math.max(0, infected - todayInfected);
                recovered = Math.max(0, recovered + recoveriesToday);
                dead = Math.max(0, dead + deathsToday);
                population = Math.max(0, population - deathsToday);
                diseaseResultDayMap.remove(dayCounter);
                adjustCountryPoints(recoveriesToday);
            }

            if (diseaseResultDayMap.isEmpty()) {
                infected = 0;
                recovered = Math.max(0, recovered + 1);
                adjustCountryPoints(1);
                isVirusDefated = true;
            }

            susceptible = Math.max(0, susceptible);
        } else {
            int newInfections;
            if (susceptible < (population * 0.1)) {
                newInfections = (int) Math.min(Math.ceil(susceptible * 0.1), susceptible);
            } else {
                double potentialInfections = infectionRate * susceptible * (((double) infected) / population);
                newInfections = (int) Math.max(1, Math.min(Math.ceil(potentialInfections), susceptible));
            }

            newInfections = Math.min(newInfections, susceptible);

            int recoverOrDeadDay = dayCounter + getRandomDays(14, 21);
            diseaseResultDayMap.put(recoverOrDeadDay, diseaseResultDayMap.getOrDefault(recoverOrDeadDay, 0) + newInfections);

            susceptible = Math.max(0, susceptible - newInfections);
            infected = Math.max(0, infected + newInfections);

            if (todayInfected > 0) {
                int recoveriesToday = Math.max(1, (int) (todayInfected * recoveryRate));
                int deathsToday = todayInfected - recoveriesToday;

                infected = Math.max(0, infected - todayInfected);
                recovered = Math.max(0, recovered + recoveriesToday);
                dead = Math.max(0, dead + deathsToday);
                population = Math.max(0, population - deathsToday);
                diseaseResultDayMap.remove(dayCounter);
                adjustCountryPoints(recoveriesToday);
            }
        }

        dayCounter++;
    }

    private void adjustCountryPoints(int recoveredToday){
        double points = (((double) recoveredToday) / population) * 100.0;
        double multiplier = GameSettings.getDifficultyLevel().getScoreModifier();
        double newPoints = points * multiplier;
        double roundedPoints = Helpers.roundToTwoDecimals(newPoints);
        increaseCountryPoints(roundedPoints);

    }


    public void buyUpgrade(Upgrade upgrade) {
        double cost = Helpers.roundToTwoDecimals(upgrade.getCost());
        if (countryPoints >= cost) {
            countryPoints -= cost;
        }
    }

    public void applyUpgrade(Upgrade upgrade, String effectKey, double effectValue ) {
        upgrades.put(upgrade, true);
        switch (effectKey) {
            case "Zaraźliwość":
                modifyInfectionRate(effectValue);
                break;
            case "Skutecznośc leczenia":
                modifyRecoveryRate(effectValue);
                break;
            case "Śmiertelność":
                modifyMortalityRate(effectValue);
                break;
        }
    }

    public void switchOfTransport(TransportType transportType){
        gameModel.disableTransport(this,transportType);
    }

    public void modifyInfectionRate(double modifier){
        this.infectionRate += modifier;
        this.infectionRate = Math.max(0, this.infectionRate);
    }

    public void modifyRecoveryRate(double modifier){
        this.recoveryRate += modifier;
        this.recoveryRate = Math.min(1, this.recoveryRate);
    }

    public void modifyMortalityRate(double modifier){
        this.mortalityRate += modifier;
        this.mortalityRate = Math.max(0, this.mortalityRate);
    }



    private int getRandomDays(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }



    private synchronized void increaseCountryPoints(double pointsToAdd) {
        this.countryPoints = Math.min(GameSettings.maxCountryPoints, this.countryPoints + pointsToAdd);
    }
}
