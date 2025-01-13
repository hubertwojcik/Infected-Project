package models.ugrades;

import interfaces.Upgrade;
import models.country.Country;

import java.util.Map;
import java.util.Set;

public class MassTesting implements Upgrade {
    private double cost = 5;

    @Override
    public String getName() {
        return "Masowe testowanie";
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public Map<String, Double> getEffects() {
        return Map.of(
                "Zaraźliwość", -0.10,
                "Zdrowienie", 0.05,
                "Śmiertelność", 0.0,
                "PKB", -0.01
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
