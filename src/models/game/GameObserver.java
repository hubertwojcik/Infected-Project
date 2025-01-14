package models.game;


    public interface GameObserver {
        void onDayUpdate(int dayCounter);
        void onGlobalStatsUpdate(int infected, int cured, int dead);
        void onSelectedCountryUpdate(String countryName, int population, int infected, int cured, int dead,  double infectedRate,double recoveryRestinatce, double moratyliRate);
        void onGameEnd();
}
