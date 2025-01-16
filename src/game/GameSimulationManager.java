package game;

import models.Transport.Transport;
import models.country.Country;

import java.util.ArrayList;
import java.util.List;
public class GameSimulationManager {
    private final List<Country> countries;
    private final List<Transport> transports;

    // Można np. przyjąć jedną globalną blokadę dla transportu:
    private final Object transportLock = new Object();

    public GameSimulationManager(List<Country> countries, List<Transport> transports) {
        this.countries = countries;
        this.transports = transports;
    }

    public void runSimulationStep() {
        runTransportPhase();

        runDiseaseSpreadPhase();
    }

    private void runTransportPhase() {
        synchronized (transportLock) {
            for (Transport transport : transports) {
                transport.executeTransportSafely();
            }
        }
    }

    private void runDiseaseSpreadPhase() {
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
                System.err.println("Simulation thread interrupted: " + e.getMessage());
            }
        }
    }
}

