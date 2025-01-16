package models.ugrades;

import interfaces.Upgrade;
import models.country.Country;

import java.util.Map;

public class SocialDistancing implements Upgrade {
    private final double cost;
    private final Map<String, Double> effects;


    public SocialDistancing(double cost, double infectivity, double recovery, double mortality) {
        this.cost = cost;
        this.effects = Map.of(
                "Zaraźliwość", infectivity,
                "Skutecznośc leczenia", recovery,
                "Śmiertelność", mortality
        );
    }

    @Override
    public String getName() {
        return "Dystans społeczny";
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
        country.buyUpgrade(this);

        for (Map.Entry<String,Double> effect : getEffects().entrySet()){
            String effectKey =effect.getKey();
            double effectValue = effect.getValue();
            country.applyUpgrade(this,effectKey,effectValue);
        }
    }
}
