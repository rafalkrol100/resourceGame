package run;

import objects.*;

import javax.swing.*;
import java.util.ArrayList;

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
        ArrayList<ArrayList<Integer>> list = Simulation.runSimulation(width, length, resourceGridOfResourceObjects, numberOfRepetitions, writeObject);
        ArrayList<Integer> neutralList = list.get(0);
        ArrayList<Integer> aggressiveList = list.get(1);

        SwingUtilities.invokeLater(() -> {
            TimeSeriesChart example = new TimeSeriesChart("Time Series Chart", neutralList, aggressiveList);
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);
            example.setVisible(true);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        });
    }
}