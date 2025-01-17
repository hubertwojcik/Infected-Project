package controllers.map;

import models.country.Country;
import models.game.GameModel;

import java.awt.*;

public class MapController {
    private final GameModel gameModel;


    public MapController(GameModel gameModel)
    {
        this.gameModel = gameModel;
    }

    public void handleMapClick(Point point) {
        for (Country country : gameModel.getCountries()) {
            if (isClickedPointCountry(point, country)) {
                gameModel.setSelectedCountry(country);
                System.out.println(country.getName());
                return;
            }
        }
        gameModel.setSelectedCountry(null);
    }

    private boolean isClickedPointCountry(Point point, Country country) {
        Rectangle countryBounds = new Rectangle(
                country.getCountryXCoordinate(),
                country.getCountryYCoordinate(),
                country.getCountryWidth(),
                country.getCountryHeight()
        );

        return countryBounds.contains(point);
    }
}
