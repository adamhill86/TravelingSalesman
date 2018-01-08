# Traveling Salesman Problem
This a Java implementation of the [traveling salesman problem](https://en.wikipedia.org/wiki/Travelling_salesman_problem).
It uses a genetic algorithm to optimize the salesman's tour.

## Evolution algorithm
* Generates a population of 100 randomly-generated tours
* For each tour in the population, parents are chosen from among those with the shortest distances
* [Order one crossover](http://www.rubicite.com/Tutorials/GeneticAlgorithms/CrossoverOperators/Order1CrossoverOperator.aspx) 
is then used to create child tours from two given parent tours
* Each child has a chance to be mutated
    * A simple swap mutation method is used
* This population of children becomes the basis of the next generation
* This method is repeated for 1000 generations

Over the generations, tour distance becomes shorter and eventually converges on a single number, usually far smaller 
than the original distances.