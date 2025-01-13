package models.map;

import models.Coordinates;

import java.util.Random;

public abstract class MapObject {
    private final int mapObjectX,mapObjectY;
    private final int mapObjectWidth,mapObjectHeight;

    public MapObject(int mapX, int mapY,int width,int height){
            this.mapObjectX =mapX;
            this.mapObjectY = mapY;
            this.mapObjectHeight=height;
            this.mapObjectWidth= width;
    }

    public int getMapObjectX() {
        return mapObjectX;
    }

    public int getMapObjectY() {
        return mapObjectY;
    }

    public int getHeight() {
        return mapObjectHeight;
    }

    public int getWidth() {
        return mapObjectWidth;
    }

    public Coordinates getCountryCenterPoint(){
        int xCenter = mapObjectX + mapObjectWidth / 2;
        int yCenter = mapObjectY + mapObjectHeight / 2;
        return new Coordinates(xCenter,yCenter);
    };

    public Coordinates getRandomLocationWithinCountry(){
        Random r = new Random();

        int randomXPosition = r.nextInt((mapObjectX + mapObjectWidth - mapObjectX) + 1) + mapObjectX;
        int randomYPosition = r.nextInt((mapObjectY + mapObjectHeight - mapObjectY) + 1) + mapObjectY;

        return new Coordinates(randomXPosition,randomYPosition);

    }
}
