package models.ugrades;

import interfaces.Upgrade;
import models.country.Country;

import java.util.Map;

public class ShipTransportDisable implements Upgrade {
    private final double cost;
    private final Map<String, Double> effects;



    public ShipTransportDisable(double cost, double infectivity, double recovery, double mortality) {
        this.cost = cost;
        this.effects = Map.of(
                "Zaraźliwość", infectivity,
                "Zdrowienie", recovery,
                "Śmiertelność", mortality
        );
    }
    @Override
    public String getName() {
        return "Wyłączenie statków";
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public Map<String, Double> getEffects() {
        return effects;
    }

    @Override
    public void applyUpgrade(Country country) {
        for (Map.Entry<String,Double> effect : getEffects().entrySet()){
            String effectKey =effect.getKey();
            double effectValue = effect.getValue();
            country.applyUpgrade(this,effectKey,effectValue);
        }
    }
}
