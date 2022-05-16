package run;

import objects.Coordinates;
import objects.Person;
import objects.Resource;
import objects.ResourceState;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        int width, length;
        int initialNumberOfNeutrals, initialNumberOfAggressive;
        int numberOfRepetitions = 100;
        boolean writeObject = false;
        initialNumberOfNeutrals = 1;
        initialNumberOfAggressive = 1;
        width = 10;
        length = 10;


        List<Person> personsList = new ArrayList<Person>();

        for (int i = 0; i < initialNumberOfNeutrals; i++) {
            personsList.add(new Person(0, personsList.size(), Person.Type.neutral, 0, true, new Coordinates(0, 0)));
        }

        for (int i = 0; i < initialNumberOfAggressive; i++) {
            personsList.add(new Person(0, personsList.size(), Person.Type.aggressive, 0, true, new Coordinates(0, 0)));
        }


        for (int i = 0; i < numberOfRepetitions; i++) {
            Resource[][] resourceGridOfResourceObjects = new Resource[width][length];
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < length; k++) {
                    resourceGridOfResourceObjects[j][k] = new Resource(new Coordinates(j, k), ResourceState.free, Optional.empty());
                }
            }
            personsList.forEach(person -> person.setResourceAmount(0));
            for (Person person : personsList) {
                if (person.isAlive()) {
                    while (Resource.isAnyResourceFree(resourceGridOfResourceObjects, width, length)) {
                        Random rand = new Random();
                        int newCoordinateX = rand.nextInt(width);
                        int newCoordinateY = rand.nextInt(length);
                        person.setCoordinates(new Coordinates(newCoordinateX, newCoordinateY));
                        if (resourceGridOfResourceObjects[newCoordinateX][newCoordinateY].getPerson().isEmpty()) {
                            person.setResourceAmount(1);
                            resourceGridOfResourceObjects[newCoordinateX][newCoordinateY].setPerson(Optional.of(person));
                            resourceGridOfResourceObjects[newCoordinateX][newCoordinateY].setResourceState(ResourceState.reserved);
                            break;
                        }
                    }
                    if (person.getResourceAmount() == 0) {
                        Random rand = new Random();
                        int newCoordinateX = rand.nextInt(width);
                        int newCoordinateY = rand.nextInt(length);
                        int opponentId = resourceGridOfResourceObjects[newCoordinateX][newCoordinateY].getPerson().get().getId();
                        Person opponent = personsList.get(opponentId);
                        if (person.getType().equals(Person.Type.neutral) && opponent.getType().equals(Person.Type.neutral)) {
                            person.setResourceAmount(0.5);
                            opponent.setResourceAmount(0.5);
                            resourceGridOfResourceObjects[newCoordinateX][newCoordinateY].setResourceState(ResourceState.taken);
                        }

                        if (person.getType().equals(Person.Type.aggressive) && opponent.getType().equals(Person.Type.neutral)) {
                            person.setResourceAmount(1);
                            opponent.setResourceAmount(0);
                            resourceGridOfResourceObjects[newCoordinateX][newCoordinateY].setResourceState(ResourceState.taken);
                        }

                        if (person.getType().equals(Person.Type.neutral) && opponent.getType().equals(Person.Type.aggressive)) {
                            person.setResourceAmount(0);
                            opponent.setResourceAmount(1);
                            resourceGridOfResourceObjects[newCoordinateX][newCoordinateY].setResourceState(ResourceState.taken);
                        }

                        if (person.getType().equals(Person.Type.aggressive) && opponent.getType().equals(Person.Type.aggressive)) {
                            person.setResourceAmount(0);
                            opponent.setResourceAmount(0);
                            resourceGridOfResourceObjects[newCoordinateX][newCoordinateY].setResourceState(ResourceState.taken);
                        }
                    }
                }
            }


            int countAggressive = (int) personsList.stream().filter(person -> person.getType().equals(Person.Type.aggressive) && person.isAlive()).count();
            int countNeutral = (int) personsList.stream().filter(person -> person.getType().equals(Person.Type.neutral) && person.isAlive()).count();
//            System.out.println("Number of aggressive persons during day: " + i + " is equal to: " + countAggressive);
//            System.out.println("Number of neutral persons during day: " + i + " is equal to: " + countNeutral);
            System.out.println("aggressive - " + countAggressive + " : " + countNeutral + " - neutral");


            for (Person person : personsList) {
                int age = person.getAge();
                person.setAge(age + 1);
            }

            for (int k = 0; k < personsList.size(); k++) {
                if (personsList.get(k).getResourceAmount() == 0) {
                    personsList.get(k).setAlive(false);
                }
            }

            for (int k = 0; k < personsList.size(); k++) {
                if (personsList.get(k).isAlive()) {
                    if (personsList.get(k).getResourceAmount() == 1) {
                        personsList.add(new Person(0, personsList.size(), personsList.get(k).getType(), 0, true, new Coordinates(0, 0)));
                    }
                }
            }


            if (writeObject) {
                personsList.stream().filter(person -> person.getType().equals(Person.Type.aggressive)).collect(Collectors.toList()).forEach(System.out::println);
                personsList.stream().filter(person -> person.getType().equals(Person.Type.neutral)).collect(Collectors.toList()).forEach(System.out::println);

            }
        }


    }
}