package interfaces;

import models.country.Country;
import java.util.Map;
public interface Upgrade {
    String getName();
    double getCost();

    Map<String, Double> getEffects();

    void applyUpgrade(Country country);
}
