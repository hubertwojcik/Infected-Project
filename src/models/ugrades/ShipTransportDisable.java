package models.ugrades;

import interfaces.Upgrade;
import models.map.Country;

import java.util.Map;

public class ShipTransportDisable implements Upgrade {
    private double cost = 5;

    @Override
    public String getName() {
        return "Wyłączenie transportu morskiego";
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public Map<String, Double> getEffects() {
        return null;
    }

    @Override
    public void applyUpgrade(Country country) {

    }
}
