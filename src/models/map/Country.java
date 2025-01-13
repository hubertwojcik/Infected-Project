package models.map;

import java.awt.*;
import java.util.*;


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
    //
    private final Object lock = new Object();
    //
    private  double gdp;
    private double gpdGrowth;
    //UPGRADES
    private final Map<Upgrade, Boolean> upgrades = new HashMap<>();




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

    public CountryColor getColor() {
        return color;
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

    public Point getMapObjectPosition() {
        return new Point(getCountryXCoordinate(), getCountryYCoordinate());
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

        double infectionRate = virus.getInfectionRate();
        double recoveryRate = 1 - virus.getRecoveryResistance();
        double mortalityRate = virus.getMortalityRate();

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

            infected -= todayInfected;
            recovered += recoveriesToday;
            dead += deathsToday;

            infectedMap.remove(dayCounter);
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



    public void adjustPopulation(PeopleTransport payload, boolean isTransportToCountry) {
        synchronized (lock) {
            if(isTransportToCountry){
                this.infected += payload.infectedPeople;
                this.population += payload.healthyPeople + payload.infectedPeople;
            }else{
                this.infected -= payload.infectedPeople;
                this.population -= payload.healthyPeople + payload.infectedPeople;
            }
//            population += delta;
        }
    }


    public int getInfected() {
        synchronized (lock) {
            return infected;
        }
    }

    public Virus getVirus() {
        return virus;
    }


    // Population adjustment

    // Helpers
    private int getRandomDays(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public void setVirus(Virus virus, int infectedPersons) {
        if (this.virus == null && virus != null) {
            this.virus = virus;
            this.infected = infectedPersons;
        }
    }
}
