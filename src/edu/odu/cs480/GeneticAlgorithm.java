package edu.odu.cs480;

import java.util.*;

public class GeneticAlgorithm {
    private Population population;
    private TourManager tourManager;
    private Random random;
    private final double MUTATION_RATE = 0.02;
    private final int POP_SIZE = 100;
    private Tour minimum;
    private Tour gen0;

    public GeneticAlgorithm(TourManager tourManager) {
        population = new Population(POP_SIZE, tourManager);
        population.initialize();
        random = new Random();
        this.tourManager = tourManager;
        minimum = new Tour(tourManager);
    }

    public void startEvolution(int numGenerations) {
        for (int i = 0; i < numGenerations; i++) {
            System.out.print("Gen " + i + " ");
            evolution();
            if (i == 0) {
                gen0 = minimum.clone();
                gen0.calculateDistance();
            }
        }
        System.out.println("Generation 0: " + gen0.getDistance());
    }

    private void evolution() {
        Population nextPopulation = new Population(POP_SIZE, tourManager);

        // crossover
        for (int i = 0; i < population.getSize(); i++) {
            Tour firstParent = selectParent();
            Tour secondParent = selectParent();
            Tour child = orderOneCrossover(firstParent, secondParent);
            nextPopulation.addTour(child);
        }

        // mutate
        for (Tour child : nextPopulation.getTours()) {
            double rand = random.nextDouble();
            if (rand < MUTATION_RATE) {
                //System.out.println("Mutation!");
                swapMutation(child);
            }
        }

        /*
        if (nextPopulation.getShortestTour().getDistance() < population.getShortestTour().getDistance()) {
            population = nextPopulation;
            minimum = population.getShortestTour();
        }*/
        population = nextPopulation;
        minimum = population.getShortestTour();
        System.out.println(minimum.getDistance());
    }

    /**
     * Selects a parent Tour for crossover.
     * Randomly selects 10 tours from the population and returns the one with the smallest distance
     * @return The tour with the smallest distance
     */
    private Tour selectParent() {
        // randomly select 10 Tours
        List<Tour> selection = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(population.getSize());
            Tour tour = population.getTour(index);
            selection.add(tour);
        }

        // Find the one with the smallest distance
        double minDistance = Double.MAX_VALUE;
        Tour min = null;
        for (Tour tour : selection) {
            if (tour.getDistance() < minDistance) {
                minDistance = tour.getDistance();
                min = tour;
            }
        }
        return min;
    }

    /**
     * This method uses order one crossover as described in the class notes to produce a child from parents 1 and 2
     * @param parent1
     * @param parent2
     * @return
     */
    public Tour orderOneCrossover(Tour parent1, Tour parent2) {
        Tour result = new Tour(tourManager);
        result.initialize();
        int setSize = tourManager.numCities() / 2;
        int rand = random.nextInt(3) - 1; // generate a random number -1 to 1 to vary the set size some
        setSize += rand;
        Set<Integer> hashSet = new HashSet<>(); // Keep track of which cities have been added

        // fill the hashset with all the cities in the tour
        for (int i = 0; i < tourManager.numCities(); i++) {
            hashSet.add(i);
        }

        // randomly select a starting position for crossover
        int i = random.nextInt(parent1.getSize() - setSize);
        int resultIndex = i;

        // copy the cities from first parent
        for (int j = 0; j < setSize; j++) {
            if (i >= parent1.getSize()) i = 0;
            if (resultIndex >= result.getSize()) resultIndex = 0;

            int cityIndex = parent1.getCity(j);
            result.setCity(resultIndex, cityIndex);
            i++;
            resultIndex++;
            // remove the city from hashset
            hashSet.remove(cityIndex);
        }

        // copy the rest from second parent
        boolean combining = true;
        resultIndex = i;
        while (combining) {
            if (i >= parent2.getSize()) i = 0;
            if (resultIndex >= result.getSize()) resultIndex = 0;

            int cityIndex = parent2.getCity(i);
            //System.out.println(cityIndex);
            if (!result.contains(cityIndex)) {
                //System.out.println("Adding " + cityIndex);
                result.setCity(resultIndex, cityIndex);
                hashSet.remove(cityIndex);
                resultIndex++;
            }
            i++;
            if (hashSet.isEmpty()) {
                combining = false;
            }
        }
        result.calculateDistance();
        return result;
    }

    /**
     * Simple swap mutation.
     * Swaps cities at two random locations in a tour
     * @param tour The tour being mutated
     */
    private void swapMutation(Tour tour) {
        // pick two positions at random
        int i = 0, j = 0;

        while (i == j) {
            i = random.nextInt(tour.getSize());
            j = random.nextInt(tour.getSize());
        }

        // Swap the cities around
        int firstCity = tour.getCity(i);
        int secondCity = tour.getCity(j);
        tour.setCity(i, secondCity);
        tour.setCity(j, firstCity);
    }
}
