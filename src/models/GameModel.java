package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private final LocalDate startDate = LocalDate.of(2024,12,31);
    private LocalDate gameDate;
    private final List<Country> countries;

    public GameModel(){
        countries = initializeCountries();
        this.gameDate = startDate;

    }

    private List<Country> initializeCountries() {
        List<Country> list = new ArrayList<>();
        list.add(new Country("Russia", 144000000, 50, 50, 200, 100 ));
        list.add(new Country("China", 1400000000 ,300, 150, 150, 100));
        list.add(new Country("India", 1300000000 ,300, 300, 100, 100));
        list.add(new Country("Japan", 126000000,500, 200, 100, 50));
        return list;
    }

    public List<Country> getCountries(){
        return countries;
    }

    public void advanceOneDay(){
        gameDate = gameDate.plusDays(1);
    }

    public LocalDate getGameDate(){
        return gameDate;
    }

}
