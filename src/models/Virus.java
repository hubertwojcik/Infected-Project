package models;

public class Virus {
    private String name;
    private double infectionRate;
    private double recoveryResistance;
    private double mortalityRate;
    private int incubationPeriod;



    public Virus(String name, double infectionRate, double recoveryResistance, double mortalityRate,int incubationPeriod) {
        this.name = name;
        this.infectionRate = infectionRate;
        this.recoveryResistance = recoveryResistance;
        this.mortalityRate = mortalityRate;
        this.incubationPeriod = incubationPeriod;

    }

    public String getName() {
        return name;
    }

    public double getInfectionRate() {
        return infectionRate;
    }

    public double getRecoveryResistance() {
        return recoveryResistance;
    }

    public double getMortalityRate() {
        return mortalityRate;
    }

    public int getIncubationPeriod() {
        return incubationPeriod;
    }

    public void mutate() {
        this.infectionRate *= 1.05;
        this.recoveryResistance *= 1.05;
    }
}
