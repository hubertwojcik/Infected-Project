package game;

import enums.CountryColor;
import enums.DifficultyLevel;
import enums.TransportType;
import interfaces.Upgrade;
import models.Transport.Transport;
import models.country.Country;
import models.game.GameModel;
import models.game.Virus;
import models.ugrades.UpgradeFactory;

import java.util.ArrayList;
import java.util.List;

public class GameDataInitializer {
    public static List<Country> initializeCountries(GameModel gameModel) {
        int mapPadding = 25;
        int countrySpacing = 2;

        List<Country> countries = new ArrayList<>();
        Country russia = new Country(
                gameModel,
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
                gameModel,
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
                gameModel,
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
        Country china = new Country(
                gameModel,
                "Chiny",
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
                gameModel,
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
                gameModel,
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
                gameModel,
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
                gameModel,
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
                gameModel,
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
                gameModel,
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



        for(Country country : countries){
            List<Upgrade> upgrades = UpgradeFactory.getUpgradesForCountry(country);
            country.initializeUpgrades(upgrades);
        }

        return countries;
    }


    public static List<Transport> initializeTransports(List<Country> countries) {
        List<Transport> transports = new ArrayList<>();
        Country russia = countries.stream().filter(c -> c.getName().equals("Rosja")).findFirst().orElse(null);
        Country mongolia = countries.stream().filter(c -> c.getName().equals("Mongolia")).findFirst().orElse(null);
        Country china = countries.stream().filter(c -> c.getName().equals("Chiny")).findFirst().orElse(null);
        Country japan = countries.stream().filter(c -> c.getName().equals("Japonia")).findFirst().orElse(null);
        Country kazakhstan = countries.stream().filter(c -> c.getName().equals("Kazakhstan")).findFirst().orElse(null);
        Country iran = countries.stream().filter(c -> c.getName().equals("Iran")).findFirst().orElse(null);
        Country pakistan = countries.stream().filter(c -> c.getName().equals("Pakistan")).findFirst().orElse(null);
        Country india = countries.stream().filter(c -> c.getName().equals("Indie")).findFirst().orElse(null);
        Country wietnam = countries.stream().filter(c -> c.getName().equals("Wietnam")).findFirst().orElse(null);
        Country indonezja = countries.stream().filter(c -> c.getName().equals("Indonezja")).findFirst().orElse(null);



        if (russia != null && china != null && japan != null && kazakhstan != null &&  iran != null && pakistan != null && india != null && wietnam != null && indonezja != null && mongolia != null) {
            Transport chinaRusPlane =new Transport(china, russia, 1000, TransportType.AIR);
            Transport rusChinaPlane = new Transport(russia, china, 1000, TransportType.AIR);
            transports.add(rusChinaPlane);
            transports.add(chinaRusPlane);

            Transport rusKazak = new Transport(russia,kazakhstan, 500, TransportType.RAILWAY);
            Transport kazakRus = new Transport(kazakhstan,russia, 1500, TransportType.RAILWAY);
            transports.add(rusKazak);
            transports.add(kazakRus);

            Transport rusMongoliaRail = new Transport(russia,mongolia, 500, TransportType.RAILWAY);
            Transport mongRussRail = new Transport(mongolia,russia, 1500, TransportType.RAILWAY);
            transports.add(rusMongoliaRail);
            transports.add(mongRussRail);

            Transport chinaJapanShip = new Transport(china,japan,300,TransportType.WATER);
            Transport japanChinaShip = new Transport(japan,china,500,TransportType.WATER);
            transports.add(chinaJapanShip);
            transports.add(japanChinaShip);

            Transport kazakIran = new Transport(kazakhstan,iran, 500, TransportType.RAILWAY);
            Transport iranKazak = new Transport(iran,kazakhstan, 350, TransportType.RAILWAY);
            transports.add(kazakIran);
            transports.add(iranKazak);

            Transport iranPakistanAir = new Transport(iran,pakistan, 500, TransportType.AIR);
            Transport pakistanIranAir = new Transport(pakistan,iran, 1500, TransportType.AIR);
            transports.add(iranPakistanAir);
            transports.add(pakistanIranAir);

            Transport pakistanIndiaRail = new Transport(pakistan,india, 2500, TransportType.RAILWAY);
            Transport indiaPakistanRail = new Transport(india,pakistan, 3500, TransportType.RAILWAY);
            transports.add(pakistanIndiaRail);
            transports.add(indiaPakistanRail);

            Transport chinaWietnamRail = new Transport(china,wietnam, 3500, TransportType.RAILWAY);
            Transport wietnamChinaRail = new Transport(wietnam,china, 2500, TransportType.RAILWAY);
            transports.add(chinaWietnamRail);
            transports.add(wietnamChinaRail);

            Transport russiaIndiaAir = new Transport(russia,india, 3500, TransportType.AIR);
            Transport indiaRussiaAir = new Transport(india,russia, 5000, TransportType.AIR);
            transports.add(russiaIndiaAir);
            transports.add(indiaRussiaAir);

            Transport indiaIndoShip = new Transport(india,indonezja, 3500, TransportType.WATER);
            Transport indonezjaIndiaShip = new Transport(indonezja,india, 3500, TransportType.WATER);
            transports.add(indiaIndoShip);
            transports.add(indonezjaIndiaShip);

            Transport wietnamIndoShip = new Transport(wietnam,indonezja, 3500, TransportType.WATER);
            Transport indoWietnamShip = new Transport(indonezja,wietnam, 3500, TransportType.WATER);
            transports.add(wietnamIndoShip);
            transports.add(indoWietnamShip);

            Transport wietnamMongoSAir = new Transport(wietnam,mongolia, 3500, TransportType.AIR);
            Transport mongoWietamAir = new Transport(mongolia,wietnam, 3500, TransportType.AIR);
            transports.add(wietnamMongoSAir);
            transports.add(mongoWietamAir);

            Transport chinaIndiaAir = new Transport(china,india, 10000, TransportType.AIR);
            Transport indiaChinaAir = new Transport(india,china, 12000, TransportType.AIR);
            transports.add(chinaIndiaAir);
            transports.add(indiaChinaAir);

            Transport japanIndoAir   = new Transport(japan,indonezja, 5000, TransportType.AIR);
            Transport indoJapanAir  = new Transport(indonezja,japan, 2000, TransportType.AIR);
            transports.add(japanIndoAir);
            transports.add(indoJapanAir);

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
