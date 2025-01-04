package models;

import config.Config;
import enums.TransportType;

public class Transport implements Runnable {
    private final Country fromCountry;
    private final Country toCountry;
    private final int dailyFlow;
    private final TransportType transportType;
    private Thread thread;

    private boolean running = false;
    private boolean isEnabled = false;

    public Transport(Country fromCountry, Country toCountry, int dailyFlow, TransportType transportType) {
        this.fromCountry = fromCountry;
        this.toCountry = toCountry;
        this.dailyFlow = dailyFlow;
        this.transportType = transportType;

    }

    public void enable() {
        if (!isEnabled) {
            isEnabled = true;
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    public void disable() {
        isEnabled = false;
        running = false;
        if (thread != null) {
            thread.interrupt();
        }
    }

    public void pause() {
        running = false;
    }

    public synchronized void resume() {
        if (isEnabled) {
            running = true;
            notify();
        }
    }


    public boolean isEnabled() {
        return isEnabled;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        while (isEnabled) {
            synchronized (this) {
                while (!running) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }

            try {
                Thread.sleep(Config.simulationInterval);
                synchronized (fromCountry) {
                    synchronized (toCountry) {

                        int flow = Math.min(dailyFlow, fromCountry.getPopulation());
                        fromCountry.decreasePopulation(flow);
                        toCountry.increasePopulation(flow);

//                        if (fromCountry.getInfected() > 0 && fromCountry.getVirus() != null ) {
//                            double infectionChance = (double) fromCountry.getInfected() / fromCountry.getPopulation();
//                            System.out.println("CHANGE TO TRANPSORT WIRUS: " + infectionChance);
//                            if (Math.random() < infectionChance) {
//                                if(toCountry.getVirus() == null){
//
//                                System.out.println("PRZETRANSPORTOWANIE WIRUSA DALEJ DO KRAJU: " + toCountry.getName() + " wirus: " + toCountry.getVirus());
//                                toCountry.setVirus(fromCountry.getVirus());
//                                }else {
//                                    int numOfInctedPeopleInTransport = (int) (dailyFlow * infectionChance);
//                                    toCountry.infected+=numOfInctedPeopleInTransport;
//                                }
//
//                            }
//                        }
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
