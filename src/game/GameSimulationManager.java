package game;

import models.game.Transport;
import models.map.Country;

import java.util.ArrayList;
import java.util.List;

public class GameSimulationManager {
    private final List<Country> countries;
    private final List<Transport> transports;

    public GameSimulationManager(List<Country> countries, List<Transport> transports) {
        this.countries = countries;
        this.transports = transports;
    }

    public void runSimulationStep() {
        List<Thread> threads = new ArrayList<>();

        for (Country country : countries) {
            Thread thread = new Thread(country::simulateInfectionSpread);
            threads.add(thread);
            thread.start();
        }

        for (Transport transport : transports) {
            Thread thread = new Thread(transport::run);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join(); // Wait for each thread to complete
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Simulation thread interrupted: " + e.getMessage());
            }
        }
    }


}
