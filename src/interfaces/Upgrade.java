package interfaces;

import models.map.Country;
import java.util.Map;
public interface Upgrade {
    String getName();
    double getCost();

    Map<String, Double> getEffects();

    void applyUpgrade(Country country);
}
