package models.country;

import java.util.*;
import java.util.List;


import enums.TransportType;
import game.GameSettings;
import interfaces.Upgrade;
import models.PeopleTransport;
import models.game.GameModel;
import models.game.Virus;
import enums.CountryColor;
import util.Helpers;

import javax.swing.*;

public class Country extends CountryCoordinates {
    private GameModel gameModel;
    private final String name;
    private final String capital;
    private int population;
    private boolean isSelected;
    int infected = 0;
    int recovered = 0;
    int dead = 0;
    private int susceptible;
    private int dayCounter = 0;
    private final Map<Integer, Integer> diseaseResultDayMap = new HashMap<>();
    private Random random = new Random();
    private final CountryColor color;
    // VIRUS
    private Virus virus;
    private double infectionRate;
    private double recoveryRate;

    private double mortalityRate;



    private boolean isVirusDefated = false;
    //UPGRADES
    private final Map<Upgrade, Boolean> upgrades = new HashMap<>();
    private double countryPoints;
    private final double countryPointsGrow = 0.25;



    private final Object lock = new Object();


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(name, country.name); // Porównanie na podstawie nazwy kraju
    }

    @Override
    public int hashCode() {
        return Objects.hash(name); // Generowanie hash na podstawie nazwy kraju
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
            this.susceptible += susceptible;
            this.population += (susceptible + recovered);
            this.recovered += recovered;
        } else {
            if (this.susceptible < susceptible) {
                susceptible = this.susceptible;
            }
            if (this.recovered < recovered) {
                recovered = this.recovered;
            }
            this.susceptible -= susceptible;
            this.population -= (susceptible + recovered);
            this.recovered -= recovered;
        }
    }



    public synchronized void SIRModelDiseaseSpreadSimulation() {
        countryPoints += Helpers.roundToTwoDecimals(countryPointsGrow);

        if (virus == null || population <= 0) return;


        if ((infected == 0 && susceptible == 0 && diseaseResultDayMap.isEmpty()) || isVirusDefated) {
            return;
        }

        int todayInfected = diseaseResultDayMap.getOrDefault(dayCounter, 0);

        if (susceptible < 1) {
            if (todayInfected > 0) {
                int recoveriesToday = Math.max(1, (int) (todayInfected * recoveryRate));
                int deathsToday = todayInfected - recoveriesToday;

                infected -= todayInfected;
                recovered += recoveriesToday;
                dead += deathsToday;
                population -= deathsToday;
                diseaseResultDayMap.remove(dayCounter);
                adjustCountryPoints(recoveriesToday);
            }


            if (diseaseResultDayMap.isEmpty()) {
                infected = 0;
                recovered += 1;
                adjustCountryPoints(1);
                isVirusDefated = true;
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(
                            null,
                            name + " już po epidemii! Wszyscy ludzie posiadają odporność!",
                            "Epidemia zwalczona!",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                });
            }

            infected = Math.max(0, infected);
            recovered = Math.max(0, recovered);
            dead = Math.max(0, dead);
            susceptible = 0;

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


            susceptible -= newInfections;
            infected += newInfections;

            if (todayInfected > 0) {
                int recoveriesToday = Math.max(1, (int) (todayInfected * recoveryRate));
                int deathsToday = todayInfected - recoveriesToday;

                infected -= todayInfected;
                recovered += recoveriesToday;
                dead += deathsToday;
                population -= deathsToday;
                diseaseResultDayMap.remove(dayCounter);
                adjustCountryPoints(recoveriesToday);
            }

            susceptible = Math.max(0, susceptible);
            infected = Math.max(0, infected);
            recovered = Math.max(0, recovered);
            dead = Math.max(0, dead);
        }
//
//        System.out.println(
//                "Day: " + dayCounter +
//                        " | Susceptible: " + susceptible +
//                        " | Infected: " + infected +
//                        " | Recovered: " + recovered +
//                        " | Dead: " + dead +
//                        " | Infected Map: " + diseaseResultDayMap
//        );

        dayCounter++;
    }


    private void adjustCountryPoints(int recoveredToday){
        double points = (((double) recoveredToday) / population) * 100.0;
        double multiplier = GameSettings.getDifficultyLevel().getScoreModifier();
        double newPoints = points * multiplier;
        double roundedPoints = Helpers.roundToTwoDecimals(newPoints);
        this.countryPoints += roundedPoints;
    }


    public void buyUpgrade(Upgrade upgrade) {
        double cost = Helpers.roundToTwoDecimals(upgrade.getCost());
        if (countryPoints >= cost) {
            countryPoints -= cost;
        } else {
            throw new IllegalStateException("Niewystarczająca liczba punktów do zakupu ulepszenia.");
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
            default:
                throw new IllegalArgumentException("Nieznany efekt: " + effectKey);
        }
    }

    public void switchOfTransport(TransportType transportType){
        System.out.println("SWITCH OFF + " + transportType);
        gameModel.disableTransport(this,transportType);
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


    private int getRandomDays(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

}
