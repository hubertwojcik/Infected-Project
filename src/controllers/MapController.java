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
    private boolean isClickedPointCountry(Point point, Country country) {
        Rectangle countryBounds = new Rectangle(
                country.getMapObjectX(),
                country.getMapObjectY(),
                country.getWidth(),
                country.getHeight()
        );

        return countryBounds.contains(point);
    }
}
