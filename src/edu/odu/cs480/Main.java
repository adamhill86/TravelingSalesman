package edu.odu.cs480;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<City> cities = readFile("wi29.txt");
        TourManager tourManager = new TourManager(cities);

        for (City city : tourManager.getCities()) {
            System.out.println(city);
        }

        GeneticAlgorithm ga = new GeneticAlgorithm(tourManager);
        ga.startEvolution(1000);
    }

    private static List<City> readFile(String filename) {
        List<City> coordinates = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            boolean startWritingCoordinates = false;

            while ((line = br.readLine()) != null) {
                if (line.equals("EOF")) break;

                if (startWritingCoordinates) {
                    String[] splitStr = line.split("\\s+"); // split on white space
                    City city = new City(Double.valueOf(splitStr[1]), Double.valueOf(splitStr[2]));
                    //System.out.println(city);
                    coordinates.add(city);
                }

                if (line.equals("NODE_COORD_SECTION")) {
                    startWritingCoordinates = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(coordinates.size());

        return coordinates;
    }
}
