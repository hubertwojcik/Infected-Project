package models;

import enums.TransportType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private final LocalDate startDate = LocalDate.of(2024,12,31);
    private LocalDate gameDate;
    private final List<Country> countries;
    private Country selectedCountry;
    private final List<Transport> transports;


    public GameModel(){
        countries = initializeCountries();
        transports = new ArrayList<>();

        initializeTransports();
        this.gameDate = startDate;
    }

    public void enableAllTransports() {
        transports.forEach(Transport::enable);
    }

    public void disableAllTransports() {
        transports.forEach(Transport::disable);
    }

    public void enableTransport(Transport transport) {
        transport.enable();
    }

    public void disableTransport(Transport transport) {
        transport.disable();
    }

    public Country getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(Country selectedCountry) {
        this.selectedCountry = selectedCountry;
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

    public List<Transport> getTransports() {
        return transports;
    }

    private List<Country> initializeCountries() {
        List<Country> list = new ArrayList<>();
        list.add(new Country("Russia", 144000000, 50, 50, 200, 100 ));
        list.add(new Country("China", 1400000000 ,300, 150, 150, 100));
        list.add(new Country("India", 1300000000 ,300, 300, 100, 100));
        list.add(new Country("Japan", 126000000,500, 200, 100, 50));
        return list;
    }

    public void initializeTransports() {
        Country russia = countries.stream().filter(c -> c.getName().equals("Russia")).findFirst().orElse(null);
        Country china = countries.stream().filter(c -> c.getName().equals("China")).findFirst().orElse(null);
        if (russia != null && china != null) {
            System.out.println("HEHEHE");
            Transport rusChina =new Transport(russia, china, 1000, TransportType.RAILWAY);
            transports.add(rusChina);
            rusChina.enable();

        }
    }

    //Transport


}
