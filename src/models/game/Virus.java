package models.game;

public class Virus {
    private String name;
    private double infectionRate;
    private double recoveryRate;
    private double mortalityRate;
    private int incubationPeriod;



    public Virus(String name, double infectionRate, double recoveryResistance, double mortalityRate,int incubationPeriod) {
        this.name = name;
        this.infectionRate = infectionRate;
        this.recoveryRate = recoveryResistance;
        this.mortalityRate = mortalityRate;
        this.incubationPeriod = incubationPeriod;

    }

    public String getName() {
        return name;
    }

    public double getInfectionRate() {
        return infectionRate;
    }

    public double getRecoveryRate() {
        return recoveryRate;
    }

    public double getMortalityRate() {
        return mortalityRate;
    }

    public int getIncubationPeriod() {
        return incubationPeriod;
    }

    public void mutate() {
        this.infectionRate *= 1.05;
        this.recoveryRate *= 1.05;
    }
}
