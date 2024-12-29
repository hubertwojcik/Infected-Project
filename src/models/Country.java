package models;

import entity.MapObject;

import java.awt.*;

public class Country  extends MapObject {
    private String name;
    private int population;
    private boolean isSelected;
    // VIRUS DATA
    int infected =0;
    int recovered = 0;
    int dead = 0;

    public Country(String name, int population, int mapX, int mapY, int width, int height) {
        super(mapX,mapY,width,height);
        this.name = name;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public Point getMapObjectPosition() {
        return new Point(getMapObjectX(), getMapObjectY());
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

    public synchronized void increasePopulation(int value) {
        this.population += value;
    }

    public synchronized void decreasePopulation(int value) {
        this.population -= Math.min(value, population);
    }
}
