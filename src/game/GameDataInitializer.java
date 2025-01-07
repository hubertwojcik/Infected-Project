package game;

import enums.CountryColor;
import enums.DifficultyLevel;
import enums.TransportType;
import models.game.Transport;
import models.game.Virus;
import models.map.Country;

import java.util.ArrayList;
import java.util.List;

public class GameDataInitializer {
    public static List<Country> initializeCountries() {
        int mapPadding = 25;
        int countrySpacing = 2;

        List<Country> countries = new ArrayList<>();
        Country russia = new Country(
                "Rosja",
                144000000,
                mapPadding,
                mapPadding,
                GameSettings.mapWidth - 2 * mapPadding - countrySpacing,
                100 - countrySpacing,
                CountryColor.RUSSIA);
        Country kazakhstan = new Country(
                "Kazakhstan",
                1000000,
                mapPadding,
                russia.getMapObjectY() + russia.getHeight() + countrySpacing,
                200 - countrySpacing,
                150 - countrySpacing,
                CountryColor.KAZAKHSTAN);
        Country mongolia = new Country(
                "Mongolia",
                1000000,
                kazakhstan.getMapObjectX() + kazakhstan.getWidth() + countrySpacing,
                russia.getMapObjectY() + russia.getHeight() + countrySpacing,
                250 - countrySpacing,
                50 - countrySpacing,
                CountryColor.MONGOLIA);
        Country china = new Country("Chiny",
                1400000000,
                mapPadding + kazakhstan.getWidth() + countrySpacing,
                mongolia.getMapObjectY() + mongolia.getHeight() + countrySpacing,
                250 - countrySpacing,
                100 - countrySpacing,
                CountryColor.CHINA);
        Country japan = new Country(
                "Japonia",
                126000000,
                china.getMapObjectX() + china.getWidth() + 50 + countrySpacing,
                150,
                50 - countrySpacing,
                200 - countrySpacing,
                CountryColor.JAPAN);
        Country iran = new Country(
                "Iran",
                126000000,
                mapPadding,
                kazakhstan.getMapObjectY() + kazakhstan.getHeight() + countrySpacing,
                150 - countrySpacing,
                125 - countrySpacing,
                CountryColor.IRAN);
        Country pakistan = new Country(
                "Pakistan",
                126000000,
                iran.getMapObjectX() + iran.getWidth() + countrySpacing,
                kazakhstan.getMapObjectY() + kazakhstan.getHeight() + countrySpacing,
                50 - countrySpacing,
                125 - countrySpacing,
                CountryColor.MALAYSIA);
        Country india = new Country(
                "Indie",
                1300000000,
                pakistan.getMapObjectX() + pakistan.getWidth() + countrySpacing,
                kazakhstan.getMapObjectY() + kazakhstan.getHeight() + countrySpacing,
                150 - countrySpacing,
                200 - countrySpacing,
                CountryColor.INDIA);
        Country vietnam = new Country(
                "Wietnam",
                126000000,
                india.getMapObjectX() + india.getWidth() + countrySpacing,
                kazakhstan.getMapObjectY() + kazakhstan.getHeight() + countrySpacing,
                50 - countrySpacing,
                125 - countrySpacing,
                CountryColor.VIETNAM);
        Country indonesia = new Country(
                "Indonesia",
                126000000,
                300,
                india.getMapObjectY() + india.getHeight() + 25 + countrySpacing,
                250 - countrySpacing,
                50 - countrySpacing,
                CountryColor.INDONESIA);

        countries.add(russia);
        countries.add(china);
        countries.add(kazakhstan);
        countries.add(mongolia);
        countries.add(india);
        countries.add(japan);
        countries.add(iran);
        countries.add(pakistan);
        countries.add(indonesia);
        countries.add(vietnam);

        return countries;
    }


    public static List<Transport> initializeTransports(List<Country> countries) {
        List<Transport> transports = new ArrayList<>();
        Country russia = countries.stream().filter(c -> c.getName().equals("Rosja")).findFirst().orElse(null);
        Country china = countries.stream().filter(c -> c.getName().equals("Chiny")).findFirst().orElse(null);
        if (russia != null && china != null) {
            Transport rusChinaRailway =new Transport(russia, china, 1000, TransportType.RAILWAY);
            transports.add(rusChinaRailway);

//            rusChinaRailway.enable();
        }
        return transports;
    }

    public static Virus initializeVirus(DifficultyLevel difficultyLevel) {


        return new Virus(
                "SUPER VIRUS",
                difficultyLevel.getInfectionRate(),
                difficultyLevel.getDeathRate(),
                difficultyLevel.getRecoveryRate(),
                difficultyLevel.getMutationInterval()
        );
    }
}
