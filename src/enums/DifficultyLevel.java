package enums;

public enum DifficultyLevel {
    EASY(0.1, 0.05, 0.995, 0.01, 20,0.5),
    MEDIUM(0.2, 0.2, 0.8, 0.02, 15,1.0),
    HARD(0.5, 0.5, 0.5, 0.05, 10, 1.5);

    private final double mutationRate;
    private final double infectionRate;
    private final double deathRate;
    private final double recoveryRate;
    private final int mutationInterval;
    private final double scoreModifier;

    DifficultyLevel(double infectionRate, double deathRate, double recoveryRate, double mutationRate, int mutationInterval,double scoreModifier) {
        this.infectionRate = infectionRate;
        this.deathRate = deathRate;
        this.recoveryRate = recoveryRate;
        this.mutationRate = mutationRate;
        this.mutationInterval = mutationInterval;
        this.scoreModifier = scoreModifier;
    }

    public double getInfectionRate() {
        return infectionRate;
    }

    public double getDeathRate() {
        return deathRate;
    }

    public double getRecoveryRate() {
        return recoveryRate;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public int getMutationInterval() {
        return mutationInterval;
    }

    public double getScoreModifier() {
        return scoreModifier;
    }

}

