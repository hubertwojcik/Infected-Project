package controllers;


import models.Country;
import models.GameModel;

import java.awt.*;

public class MapController {
    private final GameModel gameModel;
    private final GameController gameController;


    public MapController(GameModel gameModel,GameController gameController)
    {
        this.gameModel = gameModel;
        this.gameController = gameController;

    }

    public void handleMapClick(Point point) {
        for (Country country : gameModel.getCountries()) {
            if (isClickedPointCountry(point, country)) {
                gameModel.setSelectedCountry(country);
                gameController.handleCountrySidebarClick();
                return;
            }
        }

        // Jeśli kliknięto poza krajami, odznacz wszystkie
        gameModel.setSelectedCountry(null);
        gameController.handleCountrySidebarClick(); // Aktualizuj widok
    }
    private boolean isClickedPointCountry(Point point, Country country){
        int x = point.x;
        int y = point.y;
        int countryX = country.getMapObjectX();
        int countryY = country.getMapObjectY();
        int width = country.getWidth();
        int height = country.getHeight();

        return x >= countryX && x <= countryX + width && y >= countryY && y <= countryY + height;

    }

}
