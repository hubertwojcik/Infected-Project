package models;

import entity.MapObject;

public class Country  extends MapObject {
    private String name;
    private int population;

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
}
