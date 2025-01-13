package models.map;

public abstract class CountryCoordinates {
    private final int countryXCoordinate, countryYCoordinate,countryWidth, countryHeight, countryCapitalGlobalXCoordinate,countryCapitalGlobalYCoordinate,countryCapitalRelativeXCoordinate,countryCapitalRelativeYCoordinate;

    public CountryCoordinates(int mapX, int mapY, int width, int height,int countryCapitalGlobalXCoordinate, int countryCapitalGlobalYCoordinate, int countryCapitalRelativeXCoordinate,int countryCapitalRelativeYCoordinate){
            this.countryXCoordinate =mapX;
            this.countryYCoordinate = mapY;
            this.countryHeight =height;
            this.countryWidth = width;
            this.countryCapitalGlobalXCoordinate = countryCapitalGlobalXCoordinate;
            this.countryCapitalGlobalYCoordinate = countryCapitalGlobalYCoordinate;
            this.countryCapitalRelativeXCoordinate = countryCapitalRelativeXCoordinate;
            this.countryCapitalRelativeYCoordinate = countryCapitalRelativeYCoordinate;
    }

    public int getCountryXCoordinate() {
        return countryXCoordinate;
    }

    public int getCountryYCoordinate() {
        return countryYCoordinate;
    }

    public int getCountryHeight() {
        return countryHeight;
    }

    public int getCountryWidth() {
        return countryWidth;
    }

    public int getCountryCapitalGlobalXCoordinate() {
        return countryCapitalGlobalXCoordinate;
    }

    public int getCountryCapitalGlobalYCoordinate() {
        return countryCapitalGlobalYCoordinate;
    }

    public int getCountryCapitalRelativeXCoordinate() {
        return countryCapitalRelativeXCoordinate;
    }

    public int getCountryCapitalRelativeYCoordinate() {
        return countryCapitalRelativeYCoordinate;
    }
}
