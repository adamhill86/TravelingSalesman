package edu.odu.cs480;

import java.util.ArrayList;
import java.util.List;

// Holds all of the cities on a tour
public class TourManager {
    private List<City> cities;

    public TourManager(List<City> cities) {
        this.cities = new ArrayList<>(cities);
    }

    public void addCity(City city) {
        cities.add(city);
    }

    public City getCity(int index) {
        return cities.get(index);
    }

    public List<City> getCities() {
        return cities;
    }

    public int numCities() {
        return cities.size();
    }

    public int getCityIndex(City city) {
        int index = -1;

        if (cities.contains(city)) {
            index = cities.indexOf(city);
        }

        return index;
    }
}