package interfaces;


import enums.TransportType;
import models.country.Country;

public interface GameObserver {
        void onDayUpdate(int dayCounter);
        void onGlobalStatsUpdate(int infected, int cured, int dead);
        void onSelectedCountryUpdate(String countryName, double countryPoints, int population,int suspectible, int infected, int cured, int dead, double infectedRate,double recoveryRestinatce, double moratyliRate) ;
        void onGameEnd();

        void onTransportStateUpdate(Country country,TransportType transportType, boolean isEnabled);

    }
