package models.ugrades;

import interfaces.Upgrade;
import models.country.Country;

import java.util.Map;

public class MandatoryMasks implements Upgrade {
    private double cost = 5;

    @Override
    public String getName() {
        return "Obowiązkowe maseczki";
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public Map<String, Double> getEffects() {

        return Map.of(
                "Zaraźliwość", -0.15,
                "Zdrowienie", 0.0,
                "Śmiertelność", 0.0,
                "PKB", -0.003
        );
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
