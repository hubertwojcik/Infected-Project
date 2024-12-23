package models;

import java.util.List;
import java.util.ArrayList;

public class AppModel {
    private final List<Country> countries;

    public AppModel() {
        countries = initializeCountries();
    }
    private List<Country> initializeCountries() {
        List<Country> list = new ArrayList<>();
        list.add(new Country("Russia", 144000000 ));
        list.add(new Country("China", 1400000000 ));
        list.add(new Country("India", 1300000000 ));
        list.add(new Country("Japan", 126000000));
        return list;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void update() {
        // Aktualizuj logikę gry (np. model SIR)
        for (Country country : countries) {
//            country.updateSIR();
        }
    }
}
