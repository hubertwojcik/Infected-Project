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
        Country selectedCountry = null;


        for (Country country : gameModel.getCountries()) {
            int countryXLeftPosition = country.getMapObjectX();
            int countryYTopPosition = country.getMapObjectY();
            int countryXRightPosition = countryXLeftPosition + country.getWidth();
            int countryYBottomPosition = countryYTopPosition + country.getHeight();

            if (point.x >= countryXLeftPosition
                    && point.x <= countryXRightPosition
                    && point.y >= countryYTopPosition
                    && point.y <= countryYBottomPosition) {
                selectedCountry = country;
                break;
            }
        }

        if (selectedCountry != null && selectedCountry.equals(gameModel.getSelectedCountry())) {
            gameModel.setSelectedCountry(null);
        } else {
            gameModel.setSelectedCountry(selectedCountry);
        }

        for (Country country : gameModel.getCountries()) {
            country.setSelected(country.equals(gameModel.getSelectedCountry()));
        }

        gameController.updateSidebar();
    }

}
