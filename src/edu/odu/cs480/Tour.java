package edu.odu.cs480;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tour {
    private List<Integer> tour;
    private TourManager tourManager;
    private double distance;

    public Tour(TourManager tourManager) {
        tour = new ArrayList<>();
        this.tourManager = tourManager;
        distance = 0;
    }

    public Tour(List<Integer> tour, TourManager tourManager) {
        this.tour = tour;
        this.tourManager = tourManager;
    }

    public Tour clone() {
        List<Integer> newTour = new ArrayList<>();
        for (int city : tour) {
            newTour.add(city);
        }
        return new Tour(newTour, tourManager);
    }

    /**
     * Adds the City with index cityIndex from tourManager to tour
     * @param city
     */
    public void addCity(City city) {
        int index = tourManager.getCityIndex(city);

        if (index != -1) {
            tour.add(index);
        }
        calculateDistance();
    }

    public void setCity(int index, int cityID) {
        tour.set(index, cityID);
    }

    /**
     * Fills a tour with -1. This is used during crossover so individual positions in the array can be set.
     * Assumes an initially empty tour.
     */
    public void initialize() {
        for (int i = 0; i < tourManager.numCities(); i++) {
            tour.add(-1);
        }
    }

    public boolean contains(int cityID) {
        return tour.contains(cityID);
    }

    /**
     * Returns the integer stored at the given index.
     * The int returned corresponds to the position in the TourManager list.
     * Accessing TourManager(int) will give you the actual City object.
     * @param index
     * @return
     */
    public int getCity(int index) {
        return tour.get(index);
    }

    public void createRandomTour() {
        tour = new ArrayList<>();
        for (int i = 0; i < tourManager.numCities(); i++) {
            tour.add(i);
        }
        Collections.shuffle(tour);
        calculateDistance();
    }

    public double getDistance() {
        return distance;
    }

    public int getSize() {
        return tour.size();
    }

    public void calculateDistance() {
        distance = 0;

        for (int i = 0; i < tour.size() -1; i++) { // -1 because we'll be looking ahead 1
            City city1 = tourManager.getCity(tour.get(i));
            City city2 = tourManager.getCity(tour.get(i+1));
            distance += City.distance(city1, city2);
        }
        // add in the distance between the last city on the list and the first
        City first = tourManager.getCity(tour.get(0));
        City last = tourManager.getCity(tour.get(tour.size()-1));
        distance += City.distance(last, first);
    }
}
