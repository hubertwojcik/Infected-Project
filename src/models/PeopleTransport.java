package models;

public class PeopleTransport {
    public int infectedPeople;
    public int healthyPeople;

    public PeopleTransport(int infectedPeople, int healthyPeople){
        this.healthyPeople = healthyPeople;
        this.infectedPeople = infectedPeople;
    }

    public int getHealthyPeople() {
        return healthyPeople;
    }

    public int getInfectedPeople() {
        return infectedPeople;
    }
}
