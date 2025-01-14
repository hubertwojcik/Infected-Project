package game;

import enums.CountryColor;
import enums.DifficultyLevel;
import enums.TransportType;
import interfaces.Upgrade;
import models.Transport.Transport;
import models.country.Country;
import models.game.Virus;
import models.country.CountryFactory;
import models.ugrades.UpgradeFactory;

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
                1000,
                200 - countrySpacing,
                CountryColor.RUSSIA,
                "Moskwa",
                50,
                (100 - countrySpacing)/2
                );
        Country kazakhstan = new Country(
                "Kazakhstan",
                19_000_000,
                mapPadding,
                russia.getCountryYCoordinate() + russia.getCountryHeight() + countrySpacing,
                400 - countrySpacing,
                200 - countrySpacing,
                CountryColor.KAZAKHSTAN,
                "Astana",
                (200 - countrySpacing) / 2,
                60
                );
        Country mongolia = new Country(
                "Mongolia",
                3_400_000,
                kazakhstan.getCountryXCoordinate() + kazakhstan.getCountryWidth() + countrySpacing,
                russia.getCountryYCoordinate() + russia.getCountryHeight() + countrySpacing,
                450 - countrySpacing,
                50 - countrySpacing,
                CountryColor.MONGOLIA,
                "Ułan batun",
                (250 - countrySpacing ) / 2,
                20
                );
        Country china = new Country("Chiny",
                1400000000,
                mapPadding + kazakhstan.getCountryWidth() + countrySpacing,
                mongolia.getCountryYCoordinate() + mongolia.getCountryHeight() + countrySpacing,
                450 - countrySpacing,
                150 - countrySpacing,
                CountryColor.CHINA,
                "Pekin",
                450 - countrySpacing - 20,
                40
                );
        Country japan = new Country(
                "Japonia",
                126000000,
                china.getCountryXCoordinate() + china.getCountryWidth() + 50 + countrySpacing,
                250,
                50 - countrySpacing,
                250 - countrySpacing,
                CountryColor.JAPAN,
                "Tokio",
                50-countrySpacing - 15,
                (200 - countrySpacing) /2
        );
        Country iran = new Country(
                "Iran",
                89_200_200,
                mapPadding,
                kazakhstan.getCountryYCoordinate() + kazakhstan.getCountryHeight() + countrySpacing,
                200 - countrySpacing,
                125 - countrySpacing,
                CountryColor.IRAN,
                "Teheran",
                (150 - countrySpacing ) / 2,
                40

                );
        Country pakistan = new Country(
                "Pakistan",
                248_000_000,
                iran.getCountryXCoordinate() + iran.getCountryWidth() + countrySpacing,
                kazakhstan.getCountryYCoordinate() + kazakhstan.getCountryHeight() + countrySpacing,
                150 - countrySpacing,
                125 - countrySpacing,
                CountryColor.MALAYSIA,
                "Islamabad",
                (50 - countrySpacing) /2,
                20
                );
        Country india = new Country(
                "Indie",
                1_428_600_000,
                pakistan.getCountryXCoordinate() + pakistan.getCountryWidth() + countrySpacing,
                kazakhstan.getCountryYCoordinate() + kazakhstan.getCountryHeight() + countrySpacing,
                350 - countrySpacing,
                200 - countrySpacing,
                CountryColor.INDIA,
                "Nowe Dheli",
                (150 - countrySpacing) / 2,
                20
                );
        Country vietnam = new Country(
                "Wietnam",
                99_500_000,
                india.getCountryXCoordinate() + india.getCountryWidth() + countrySpacing,
                kazakhstan.getCountryYCoordinate() + kazakhstan.getCountryHeight() + countrySpacing,
                150 - countrySpacing,
                125 - countrySpacing,
                CountryColor.VIETNAM,
                "Laos",
                (50 - countrySpacing) / 2,10
                );
        Country indonesia = new Country(
                "Indonezja",
                277_500_000,
                500,
                india.getCountryYCoordinate() + india.getCountryHeight() + 25 + countrySpacing,
                350 - countrySpacing,
                50 - countrySpacing,
                CountryColor.INDONESIA,
                "Dżakarta",
                (250 - countrySpacing) / 2,
                10
        );
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

//        List<Country> countries = CountryFactory.getCountries();
//USE FACTORY

        for(Country country : countries){
            List<Upgrade> upgrades = UpgradeFactory.getUpgradesForCountry(country);
            country.initializeUpgrades(upgrades);
        }

        return countries;
    }


    public static List<Transport> initializeTransports(List<Country> countries) {
        List<Transport> transports = new ArrayList<>();
        Country russia = countries.stream().filter(c -> c.getName().equals("Rosja")).findFirst().orElse(null);
        Country china = countries.stream().filter(c -> c.getName().equals("Chiny")).findFirst().orElse(null);
        Country japan = countries.stream().filter(c -> c.getName().equals("Japonia")).findFirst().orElse(null);
        if (russia != null && china != null && japan != null) {
            Transport chinaRusPlane =new Transport(china, russia, 1000, TransportType.AIR);
            Transport rusChinaPlane = new Transport(russia, china, 700, TransportType.AIR);
            transports.add(rusChinaPlane);
            transports.add(chinaRusPlane);

            Transport chinaJapanShip = new Transport(china,japan,300,TransportType.WATER);
            transports.add(chinaJapanShip);
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
