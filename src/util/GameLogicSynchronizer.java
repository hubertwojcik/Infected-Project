package util;

import models.Transport.Transport;
import models.country.Country;

import java.util.ArrayList;
import java.util.List;
public class GameLogicSynchronizer {
    private final List<Country> countries;
    private final List<Transport> transports;


    private final Object transportLock = new Object();

    public GameLogicSynchronizer(List<Country> countries, List<Transport> transports) {
        this.countries = countries;
        this.transports = transports;
    }

    public void simulateGameLogic() {
        simulateTransportation();
        simulateDiseaseSpreading();
    }

    private void simulateTransportation() {
        synchronized (transportLock) {
            for (Transport transport : transports) {
                transport.transportPeoplWithSync();
            }
        }
    }

    private void simulateDiseaseSpreading() {
        List<Thread> threads = new ArrayList<>();
        for (Country country : countries) {
            Thread thread = new Thread(() -> {
                synchronized (country) {
                    country.SIRModelDiseaseSpreadSimulation();
                }
            });
            threads.add(thread);
            thread.start();
        }


        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Błąd, symulacja została przerwana: " + e.getMessage());
            }
        }
    }


}

