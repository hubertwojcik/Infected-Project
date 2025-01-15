package models.country;

import java.util.*;
import java.util.List;


import game.GameSettings;
import interfaces.Upgrade;
import models.PeopleTransport;
import models.game.Virus;
import enums.CountryColor;

import javax.swing.*;

public class Country extends CountryCoordinates {
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
    //
//    private final Object lock = new Object();

    private boolean isVirusDefated = false;

    //UPGRADES
    private final Map<Upgrade, Boolean> upgrades = new HashMap<>();
    private double countryPoints;
    private final double countryPointsGrow = 0.1;


    // Dodatkowy lock, jeśli chcemy blokować manualnie w metodach getPopulation(), itp.
    private final Object lock = new Object();


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
            // Zabezpieczenie przed ujemnymi wartościami
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

//    public synchronized void handleImmigrationAndEmigration(PeopleTransport payload, boolean isTransportToCountry){
//            int recovered = payload.recoveredPeople;
//            int susceptible = payload.susceptiblePeople;
//            if(isTransportToCountry){
//                this.susceptible += susceptible;
//                this.population += (susceptible + recovered);
//                this.recovered += recovered;
//            }else{
//                this.susceptible -= susceptible;
//                this.population -= (susceptible + recovered);
//                this.recovered -= recovered;
//            }
//    }

    public synchronized void SIRModelDiseaseSpreadSimulation() {
        countryPoints += Math.round(countryPointsGrow);

        if (virus == null || population <= 0) return;

        // Sprawdzanie końca epidemii
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

            // Gdy mapa wyników choroby jest pusta, epidemia się kończy
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

            // Zabezpieczenie wartości przed ujemnymi liczbami
            infected = Math.max(0, infected);
            recovered = Math.max(0, recovered);
            dead = Math.max(0, dead);
            susceptible = 0;

        } else {
            int newInfections;

            // Gdy liczba podatnych jest mniejsza niż 10% populacji
            if (susceptible < (population * 0.1)) {
                newInfections = (int) Math.min(Math.ceil(susceptible * 0.1), susceptible);
            } else {
                double potentialInfections = infectionRate * susceptible * (((double) infected) / population);
                newInfections = (int) Math.max(1, Math.min(Math.ceil(potentialInfections), susceptible));
            }

            // Upewnij się, że newInfections nie przekracza dostępnej liczby podatnych
            newInfections = Math.min(newInfections, susceptible);

            // Aktualizacja mapy choroby
            int recoverOrDeadDay = dayCounter + getRandomDays(14, 21);
            diseaseResultDayMap.put(recoverOrDeadDay, diseaseResultDayMap.getOrDefault(recoverOrDeadDay, 0) + newInfections);

            // Aktualizacja zmiennych
            susceptible -= newInfections;
            infected += newInfections;

            // Proces zdrowienia i śmiertelności
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

            // Zabezpieczenie wartości przed ujemnymi liczbami
            susceptible = Math.max(0, susceptible);
            infected = Math.max(0, infected);
            recovered = Math.max(0, recovered);
            dead = Math.max(0, dead);
        }

        // Debugging: Wyświetlanie stanu symulacji
        System.out.println(
                "Day: " + dayCounter +
                        " | Susceptible: " + susceptible +
                        " | Infected: " + infected +
                        " | Recovered: " + recovered +
                        " | Dead: " + dead +
                        " | Infected Map: " + diseaseResultDayMap
        );

        dayCounter++;
    }

//    public synchronized void SIRModelDiseaseSpreadSimulation() {
//        countryPoints += Math.round(countryPointsGrow);
//
//        if (virus == null || population <= 0) return;
//
//
//        if((infected == 0 && susceptible == 0 && diseaseResultDayMap.isEmpty()) || isVirusDefated){
//            return;
//        }
//
//        int todayInfected = diseaseResultDayMap.getOrDefault(dayCounter, 0);
//
//        if (susceptible < 1) {
//            if (todayInfected > 0) {
//                int recoveriesToday = Math.max(1, (int) (todayInfected * recoveryRate));
//                int deathsToday = todayInfected - recoveriesToday;
//
//                infected -= todayInfected;
//                recovered += recoveriesToday;
//                dead += deathsToday;
//                population -= deathsToday;
//                diseaseResultDayMap.remove(dayCounter);
//                adjustCountryPoints(recoveriesToday);
//            }
//
//
//            if (diseaseResultDayMap.isEmpty()) {
//                infected = 0;
//                recovered+= 1 ;
//                adjustCountryPoints(1);
//                SwingUtilities.invokeLater(() -> {
//                    JOptionPane.showMessageDialog(
//                            null,
//                            name + " już po epidemii! Wszyscy ludzie posiadają odporność!",
//                            "Epidemia zwalczona!",
//                            JOptionPane.INFORMATION_MESSAGE
//                    );
//                });
//            }
//
//            infected = Math.max(0, infected);
//            recovered = Math.max(0, recovered);
//            dead = Math.max(0, dead);
//            susceptible = 0;
//            isVirusDefated = true;
//
//        } else {
//
//            int newInfections;
//            if (susceptible < (population * 0.1)) {
//                newInfections = (int) Math.ceil(susceptible * 0.1);
//            } else {
//                double potentialInfections = infectionRate * susceptible * (((double) infected) / population);
//                newInfections = (int) Math.max(1, Math.min(Math.ceil(potentialInfections), susceptible));
//            }
//
//            int recoverOrDeadDay = dayCounter + getRandomDays(14, 21);
//            diseaseResultDayMap.put(recoverOrDeadDay, diseaseResultDayMap.getOrDefault(recoverOrDeadDay, 0) + newInfections);
//
//            susceptible -= newInfections;
//            infected += newInfections;
//
//
//            if (todayInfected > 0) {
//                int recoveriesToday = Math.max(1, (int) (todayInfected * recoveryRate));
//                int deathsToday = todayInfected - recoveriesToday;
//
//                infected -= todayInfected;
//                recovered += recoveriesToday;
//                dead += deathsToday;
//                population -= deathsToday;
//                diseaseResultDayMap.remove(dayCounter);
//                adjustCountryPoints(recoveriesToday);
//            }
//
//            susceptible = Math.max(0, susceptible);
//            infected = Math.max(0, infected);
//            recovered = Math.max(0, recovered);
//            dead = Math.max(0, dead);
//        }
//
//
//
//            System.out.println(
//                    "Day: " + dayCounter +
//                            " | Susceptible: " + susceptible +
//                            " | Infected: " + infected +
//                            " | Recovered: " + recovered +
//                            " | Dead: " + dead + " | Infceted Map: " + diseaseResultDayMap
//            );
//
//        dayCounter++;
//    }



    private void adjustCountryPoints(int recoveredToday){
        double points = (((double) recoveredToday) / population) * 100.0;
        double multiplier = GameSettings.getDifficultyLevel().getScoreModifier();
        double newPoints = points * multiplier;
        double roundedPoints = Math.round(newPoints * 100.0) / 100.0;
        this.countryPoints += roundedPoints;
    }


    public void buyUpgrade(Upgrade upgrade) {
        double cost = upgrade.getCost();
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
