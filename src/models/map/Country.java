package models.map;

import java.awt.*;
import java.util.*;

import models.game.Virus;
import enums.CountryColor;

public class Country extends MapObject {
    private String name;
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

    public Country(String name, int population, int mapX, int mapY, int width, int height,CountryColor color) {
        super(mapX, mapY, width, height);
        this.name = name;
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

    public int getPopulation() {
        return population;
    }

    public int getRecovered() {
        return recovered;
    }

    public int getInfected() {
        return infected;
    }

    public int getDead() {
        return dead;
    }

    public Point getMapObjectPosition() {
        return new Point(getMapObjectX(), getMapObjectY());
    }

    public int getMapObjectX() {
        return super.getMapObjectX();
    }

    public int getMapObjectY() {
        return super.getMapObjectY();
    }

    public int getMapObjectWidth() {
        return super.getWidth();
    }

    public int getMapObjectHeight() {
        return super.getHeight();
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    // VIRUS LOGIC
    public synchronized void simulateInfectionSpread() {
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

    // Population adjustment
    //OSOBY PRZYBYWAJACE MOGA BYC CHORE
    public synchronized void increasePopulation(int value) {
        this.population += value;
        this.susceptible += value;
    }

    //OSOBY WYJEZDZAJACE MOGA CHORE
    public synchronized void decreasePopulation(int value) {
        int decreaseAmount = Math.min(value, population);
        this.population -= decreaseAmount;
        System.out.println("Value: "+value );

        if (susceptible >= decreaseAmount) {
            this.susceptible -= decreaseAmount;
        } else {
            int remaining = decreaseAmount - susceptible;
            this.susceptible = 0;
            if (infected >= remaining) {
                this.infected -= remaining;
            } else {
                int remainingAfterInfected = remaining - infected;
                this.infected = 0;
                this.recovered = Math.max(0, recovered - remainingAfterInfected);
            }
        }
    }

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
