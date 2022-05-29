package objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Person {
    int age;
    int id;
    Type type;
    double resourceAmount;
    boolean isAlive;
    Coordinates coordinates;

    public static List<Person> personsList = new ArrayList<>();

    public static void initiatePersons(int initialNumberOfNeutrals, int initialNumberOfAggressive) {
        for (int i = 0; i < initialNumberOfNeutrals; i++) {
            personsList.add(new Person(0, personsList.size(), Person.Type.neutral, 0, true, new Coordinates(0, 0)));
        }

        for (int i = 0; i < initialNumberOfAggressive; i++) {
            personsList.add(new Person(0, personsList.size(), Person.Type.aggressive, 0, true, new Coordinates(0, 0)));
        }
    }

    public static void dailyRoutine(Person person, int width, int length, Resource[][] resourceGridOfResourceObjects) {
        if (person.isAlive()) {
            searchForResources(person, length, width, resourceGridOfResourceObjects);
        }
    }

    private static void searchForResources(Person person, int length, int width, Resource[][] resourceGridOfResourceObjects) {
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
            fight(person, width, length, resourceGridOfResourceObjects);
        }
    }

    private static void fight(Person person, int width, int length, Resource[][] resourceGridOfResourceObjects) {
        Random rand = new Random();
        int newCoordinateX = rand.nextInt(width);
        int newCoordinateY = rand.nextInt(length);
        int opponentId = resourceGridOfResourceObjects[newCoordinateX][newCoordinateY].getPerson().get().getId();
        Person opponent = Person.personsList.get(opponentId);
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

    public Person(int age, int id, Type type, double resourceAmount, boolean isAlive, Coordinates coordinates) {
        this.age = age;
        this.id = id;
        this.type = type;
        this.resourceAmount = resourceAmount;
        this.isAlive = isAlive;
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", id=" + id +
                ", type=" + type +
                ", resourceAmount=" + resourceAmount +
                ", isAlive=" + isAlive +
                ", coordinates=" + coordinates +
                '}';
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public double getResourceAmount() {
        return resourceAmount;
    }

    public void setResourceAmount(double resourceAmount) {
        this.resourceAmount = resourceAmount;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public enum Type {
        neutral,
        aggressive
    }
}
