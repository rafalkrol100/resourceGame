package run;

import objects.*;

public class Main {
    public static void main(String[] args) {
        int initialNumberOfNeutrals = 3;
        int initialNumberOfAggressive = 1;
        int width = 10;
        int length = 10;
        int numberOfRepetitions = 100;
        boolean writeObject = false;

        ResourceGrid resourceGrid = new ResourceGrid(width, length);
        Resource[][] resourceGridOfResourceObjects = resourceGrid.getResourceGridOfResourceObjects();

        Person.initiatePersons(initialNumberOfNeutrals, initialNumberOfAggressive);
        Simulation.runSimulation(width, length, resourceGridOfResourceObjects, numberOfRepetitions, writeObject);
    }
}