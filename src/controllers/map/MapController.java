package controllers.map;


import controllers.game.GameController;
import models.country.Country;
import models.game.GameModel;

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
                gameModel.setSelectedCountry(country); // Powiadamia obserwatorów
                System.out.println(country.getName());
                return;
            }
        }
        gameModel.setSelectedCountry(null); // Brak wybranego kraju
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
