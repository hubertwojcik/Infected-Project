public class Country {
    private String name;
    private int population;

    Country(String name, int population){
        this.name = name;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }
}
