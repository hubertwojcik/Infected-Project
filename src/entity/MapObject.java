package entity;

public class MapObject {
    private final int mapObjectX,mapObjectY;
    private final int mabObjectWidth,mapObjectHeight;

    public MapObject(int mapX, int mapY,int width,int height){
            this.mapObjectX =mapX;
            this.mapObjectY = mapY;
            this.mapObjectHeight=height;
            this.mabObjectWidth= width;
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
        return mabObjectWidth;
    }
}
