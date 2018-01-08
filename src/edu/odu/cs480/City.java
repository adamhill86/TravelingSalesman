package edu.odu.cs480;

public class City {
    private double x, y;

    public City(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public City() {
        x = 0.0;
        y = 0.0;
    }

    public City(City other) {
        this.x = other.getX();
        this.y = other.getY();
    }

    public City clone() {
        return new City(x, y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "X: " + x + ", Y: " + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (Double.compare(city.x, x) != 0) return false;
        return Double.compare(city.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * Calculates the distance between two cities
     * @param a The first point
     * @param b The second point
     * @return The distance between two cities
     */
    public static double distance(City a, City b) {
        double xPart = Math.pow(b.getX() - a.getX(), 2);
        double yPart = Math.pow(b.getY() - a.getY(), 2);
        return Math.sqrt(xPart + yPart);
    }
}