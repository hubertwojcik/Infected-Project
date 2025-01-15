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
        // Krok 1: Wykonujemy transport sekwencyjnie (synchronizowany), aby mieć pewność, że dane się "ustabilizują".
        runTransportPhase();

        // Krok 2: Rozprzestrzenianie wirusa w każdym kraju w osobnych wątkach
        runDiseaseSpreadPhase();
    }

    private void runTransportPhase() {
        // Zamykamy na "transportLock", by nigdzie indziej w tym samym czasie nie wywołać transportu:
        synchronized (transportLock) {
            for (Transport transport : transports) {
                // W executeTransport mamy wewnątrz synchronized(fromCountry) oraz synchronized(toCountry).
                // Dodatkowo – by uniknąć deadlocka, możemy je blokować w stałej kolejności:
                transport.executeTransportSafely();
            }
        }
    }

    private void runDiseaseSpreadPhase() {
        List<Thread> threads = new ArrayList<>();
        for (Country country : countries) {
            Thread thread = new Thread(() -> {
                // Z uwagi na to, że w jednym kroku SIR
                // nie wykonujemy już żadnego transportu,
                // wystarczy lock na samym kraju:
                synchronized (country) {
                    country.SIRModelDiseaseSpreadSimulation();
                }
            });
            threads.add(thread);
            thread.start();
        }

        // Czekamy, aż wszystkie wątki skończą liczyć.
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
//public class GameSimulationManager {
//    private final List<Country> countries;
//    private final List<Transport> transports;
//
//    public GameSimulationManager(List<Country> countries, List<Transport> transports) {
//        this.countries = countries;
//        this.transports = transports;
//    }
//
//    public void runSimulationStep() {
//        // Step 1: Execute transport operations sequentially
//        synchronized (this) {
//            for (Transport transport : transports) {
//                transport.executeTransport();
//            }
//        }
//
//        // Step 2: Run country simulations concurrently
//        List<Thread> threads = new ArrayList<>();
//
//        for (Country country : countries) {
//            Thread thread = new Thread(() -> {
//                synchronized (country) {
//                    country.SIRModelDiseaseSpreadSimulation();
//                }
//            });
//            threads.add(thread);
//            thread.start();
//        }
//
//        // Step 3: Ensure all threads complete before moving on
//        for (Thread thread : threads) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                System.err.println("Simulation thread interrupted: " + e.getMessage());
//            }
//        }
//    }
//
//
//}
