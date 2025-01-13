package models.country;
import enums.CountryColor;
import game.GameSettings;

import models.country.Country;


import java.util.List;

public class CountryFactory {
    public static List<Country> getCountries() {
        int mapPadding = 25;
        int countrySpacing = 2;
        int mapFrstStep = mapPadding;
        int firstMapCountriesHeight = 100;
        int secondMapCountriesHeight = 150;
        int mapSecondStep =  mapPadding + firstMapCountriesHeight + countrySpacing;
        int mapThirdStep = mapSecondStep + secondMapCountriesHeight +  countrySpacing;


        int russiaX = mapPadding;
        int russiaY = mapFrstStep;
        int russiaWidth = GameSettings.mapWidth - 2 * mapPadding - countrySpacing;
        int russiaHeight = firstMapCountriesHeight ;

        int kazakhstanX = mapPadding;
        int kazakhstanY =mapSecondStep;
        int kazakhstanWidth = 200 - countrySpacing;
        int kazakhstanHeight = secondMapCountriesHeight ;

        int mongoliaX = kazakhstanX + kazakhstanWidth + countrySpacing;
        int mongoliaY = mapSecondStep;
        int mongoliaWidth = 250 - countrySpacing;
        int mongoliaHeight = 50 - countrySpacing;

        int chinaX = kazakhstanX + kazakhstanWidth + countrySpacing;
        int chinaY = mongoliaY + mongoliaHeight + countrySpacing;
        int chinaWidth = 250 - countrySpacing;
        int chinaHeight = 100 ;

        int japanX = chinaX + chinaWidth + 50 + countrySpacing;
        int japanY = 150;
        int japanWidth = 50 - countrySpacing;
        int japanHeight = 200 - countrySpacing;

        int iranX = mapPadding;
        int iranY = mapThirdStep;
        int iranWidth = 150 - countrySpacing;
        int iranHeight = 125 ;

        int pakistanX = iranX + iranWidth + countrySpacing;
        int pakistanY = mapThirdStep;
        int pakistanWidth = 50 - countrySpacing;
        int pakistanHeight = 125 ;

        int indiaX = pakistanX + pakistanWidth + countrySpacing;
        int indiaY = mapThirdStep;
        int indiaWidth = 150 - countrySpacing;
        int indiaHeight = 200 ;

        int vietnamX = indiaX + indiaWidth + countrySpacing;
        int vietnamY = mapThirdStep;
        int vietnamWidth = 50 - countrySpacing;
        int vietnamHeight = 125 ;

        int indonesiaX = 300;
        int indonesiaY = indiaY + indiaHeight + 25 + countrySpacing;
        int indonesiaWidth = 250 - countrySpacing;
        int indonesiaHeight = 50 - countrySpacing;

        return List.of(
                new Country("Rosja", 144000000, russiaX, russiaY, russiaWidth, russiaHeight, CountryColor.RUSSIA, "Moskwa", 50, russiaHeight / 2),
                new Country("Kazakhstan", 19000000, kazakhstanX, kazakhstanY, kazakhstanWidth, kazakhstanHeight, CountryColor.KAZAKHSTAN, "Astana", kazakhstanWidth / 2, 60),
                new Country("Mongolia", 3400000, mongoliaX, mongoliaY, mongoliaWidth, mongoliaHeight, CountryColor.MONGOLIA, "Ułan Bator", mongoliaWidth / 2, 20),
                new Country("Chiny", 1400000000, chinaX, chinaY, chinaWidth, chinaHeight, CountryColor.CHINA, "Pekin", chinaWidth - 20, 40),
                new Country("Japonia", 126000000, japanX, japanY, japanWidth, japanHeight, CountryColor.JAPAN, "Tokio", japanWidth - 15, japanHeight / 2),
                new Country("Iran", 89200200, iranX, iranY, iranWidth, iranHeight, CountryColor.IRAN, "Teheran", iranWidth / 2, 40),
                new Country("Pakistan", 248000000, pakistanX, pakistanY, pakistanWidth, pakistanHeight, CountryColor.MALAYSIA, "Islamabad", pakistanWidth / 2, 20),
                new Country("Indie", 1428600000, indiaX, indiaY, indiaWidth, indiaHeight, CountryColor.INDIA, "Nowe Delhi", indiaWidth / 2, 20),
                new Country("Wietnam", 99500000, vietnamX, vietnamY, vietnamWidth, vietnamHeight, CountryColor.VIETNAM, "Laos", vietnamWidth / 2, 10),
                new Country("Indonesia", 277500000, indonesiaX, indonesiaY, indonesiaWidth, indonesiaHeight, CountryColor.INDONESIA, "Dżakarta", indonesiaWidth / 2, 10)
        );
    };
}
