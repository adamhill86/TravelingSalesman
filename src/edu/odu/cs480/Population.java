package edu.odu.cs480;

import java.util.ArrayList;
import java.util.List;

public class Population {
    private List<Tour> tours;
    private TourManager tourManager;
    private int size;

    public Population(int size, TourManager tourManager) {
        tours = new ArrayList<>();
        this.size = size;
        this.tourManager = tourManager;
    }

    public void initialize() {
        for (int i = 0; i < size; i++) {
            Tour tour = new Tour(tourManager);
            tour.createRandomTour();
            tours.add(tour);
        }
    }

    public void addTour(Tour tour) {
        tours.add(tour);
    }

    public Tour getShortestTour() {
        double minDistance = Double.MAX_VALUE;
        Tour shortestTour = new Tour(tourManager);

        for (Tour tour : tours) {
            if (tour.getDistance() < minDistance) {
                minDistance = tour.getDistance();
                shortestTour = tour;
            }
        }
        return shortestTour;
    }

    public Tour getTour(int index) {
        return tours.get(index);
    }

    public List<Tour> getTours() {
        return tours;
    }

    public int getSize() {
        return tours.size();
    }
}
