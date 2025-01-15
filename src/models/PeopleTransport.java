package models;

public class PeopleTransport {

    public final int susceptiblePeople;
    public final int recoveredPeople;

    public PeopleTransport(int susceptiblePeople, int recoveredPeople) {
        this.susceptiblePeople = susceptiblePeople;
        this.recoveredPeople = recoveredPeople;
    }
}