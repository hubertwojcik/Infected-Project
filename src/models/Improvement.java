package models;

public class Improvement {
    private final String name;
    private final double infectionReduction;
    private final double recoveryBoost;
    private final double mortalityReduction;
    private final int cost;

    public Improvement(String name, double infectionReduction, double recoveryBoost, double mortalityReduction, int cost) {
        this.name = name;
        this.infectionReduction = infectionReduction;
        this.recoveryBoost = recoveryBoost;
        this.mortalityReduction = mortalityReduction;
        this.cost = cost;
    }

    public double getInfectionReduction() {
        return infectionReduction;
    }

    public double getRecoveryBoost() {
        return recoveryBoost;
    }

    public double getMortalityReduction() {
        return mortalityReduction;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }
}
