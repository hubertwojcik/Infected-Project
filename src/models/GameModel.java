package models;

import controllers.GameController;
import enums.GameState;
import enums.TransportType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GameModel implements Runnable{
    GameController gameController;
    //
    private LocalDate gameDate;
    private int dayCounter = 0;
    // COUNTRIES
    private final List<Country> countries;
    private Country selectedCountry;
    private final List<Transport> transports;
    private boolean isRunning = false;
    private Thread gameThread;
    //
    private GameState gameState = GameState.NOT_STARTED;

    public GameModel(GameController gameController){
        this.gameController= gameController;
        countries = initializeCountries();
        transports = new ArrayList<>();
        initializeTransports();
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getDayCounter() {
        return dayCounter;
    }

    public void updateGameState() {
        if (gameState == GameState.PLAYING) {
            // Synchronizuj kraje
            synchronizeCountries();
            // Synchronizuj transporty
            synchronizeTransports();
            // Zwiększ datę gry
            dayCounter++;

        }
    }

    public void startGame() {
        if (gameThread == null || !gameThread.isAlive()) {
            isRunning = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    public void pauseGame() {
        isRunning = false;
    }

    public void stopGame() {
        isRunning = false;
        if (gameThread != null) {
            gameThread.interrupt();
        }
    }

    public Country getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(Country selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

    public List<Country> getCountries(){
        return countries;
    }

    @Override
    public void run() {
        while (isRunning) {
            if (gameState == GameState.PLAYING) {
                updateGameState();
                gameController.updateGameViews();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void synchronizeCountries() {
        List<Thread> countryThreads = new ArrayList<>();

        for (Country country : countries) {
            Thread thread = new Thread(country::simulateInfectionSpread);
            countryThreads.add(thread);
            thread.start();
        }


        for (Thread thread : countryThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Wątek synchronizacji kraju został przerwany: " + e.getMessage());
            }
        }
    }


    private void synchronizeTransports() {
        List<Thread> transportThreads = new ArrayList<>();

        for (Transport transport : transports) {
            Thread thread = new Thread(transport::run);
            transportThreads.add(thread);
            thread.start();
        }


        for (Thread thread : transportThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Wątek synchronizacji transportu został przerwany: " + e.getMessage());
            }
        }
    }



    private List<Country> initializeCountries() {
        List<Country> list = new ArrayList<>();
        Country russia =new Country("Russia", 144000000, 50, 50, 200, 100 );
        list.add(russia);
        Country china =new Country("China", 1400000000 ,300, 150, 150, 100);
        china.setVirus(new Virus("COVID-19", 0.4, 0.01, 0.002,14),1);
        list.add(china);
        list.add(new Country("India", 1300000000 ,300, 300, 100, 100));
        list.add(new Country("Japan", 126000000,500, 200, 100, 50));
        return list;
    }

    private void initializeTransports() {
        Country russia = countries.stream().filter(c -> c.getName().equals("Russia")).findFirst().orElse(null);
        Country china = countries.stream().filter(c -> c.getName().equals("China")).findFirst().orElse(null);
        if (russia != null && china != null) {
            Transport rusChina =new Transport(russia, china, 1000, TransportType.RAILWAY);
            Transport chinaRus = new Transport(china, russia, 2000, TransportType.AIR);

            transports.add(rusChina);
//            transports.add(chinaRus);
//            rusChina.enable();
//            chinaRus.enable();
//            System.out.println(chinaRus.isEnabled());


        }
    }




}
