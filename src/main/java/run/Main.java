package run;

import java.util.*;
import java.util.stream.Collectors;

enum ResourceState {
    free, reserved, taken
}

public class Main {
    public static boolean isAnyResourceFree(Resource[][] resourceGridState, int width, int length) {
        for (int i = 0; i < width; i++) {
            for (int k = 0; k < length; k++) {
                if (resourceGridState[i][k].resourceState == ResourceState.free)
                    return true;
            }
        }
        return false;
    }

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
                    while (isAnyResourceFree(resourceGridOfResourceObjects, width, length)) {
                        Random rand = new Random();
                        int newCoordinateX = rand.nextInt(width);
                        int newCoordinateY = rand.nextInt(length);
                        person.setCoordinates(new Coordinates(newCoordinateX, newCoordinateY));
                        if (resourceGridOfResourceObjects[newCoordinateX][newCoordinateY].person.isEmpty()) {
                            person.setResourceAmount(1);
                            resourceGridOfResourceObjects[newCoordinateX][newCoordinateY].person = Optional.of(person);
                            resourceGridOfResourceObjects[newCoordinateX][newCoordinateY].resourceState = ResourceState.reserved;
                            break;
                        }
                    }
                    if (person.resourceAmount == 0) {
                        Random rand = new Random();
                        int newCoordinateX = rand.nextInt(width);
                        int newCoordinateY = rand.nextInt(length);
                        int opponentId = resourceGridOfResourceObjects[newCoordinateX][newCoordinateY].person.get().id;
                        Person opponent = personsList.get(opponentId);
                        if (person.getType().equals(Person.Type.neutral) && opponent.getType().equals(Person.Type.neutral)) {
                            person.setResourceAmount(0.5);
                            opponent.setResourceAmount(0.5);
                            resourceGridOfResourceObjects[newCoordinateX][newCoordinateY].resourceState = ResourceState.taken;
                        }

                        if (person.getType().equals(Person.Type.aggressive) && opponent.getType().equals(Person.Type.neutral)) {
                            person.setResourceAmount(1);
                            opponent.setResourceAmount(0);
                            resourceGridOfResourceObjects[newCoordinateX][newCoordinateY].resourceState = ResourceState.taken;
                        }

                        if (person.getType().equals(Person.Type.neutral) && opponent.getType().equals(Person.Type.aggressive)) {
                            person.setResourceAmount(0);
                            opponent.setResourceAmount(1);
                            resourceGridOfResourceObjects[newCoordinateX][newCoordinateY].resourceState = ResourceState.taken;
                        }

                        if (person.getType().equals(Person.Type.aggressive) && opponent.getType().equals(Person.Type.aggressive)) {
                            person.setResourceAmount(0);
                            opponent.setResourceAmount(0);
                            resourceGridOfResourceObjects[newCoordinateX][newCoordinateY].resourceState = ResourceState.taken;
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
                if (personsList.get(k).isAlive) {
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

class Person {
    int age;
    int id;
    Type type;
    double resourceAmount;
    boolean isAlive;
    Coordinates coordinates;

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

    enum Type {
        neutral,
        aggressive
    }
}

class Coordinates {
    int x;
    int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

class Resource {
    Coordinates coordinates;
    ResourceState resourceState;
    Optional<Person> person;

    public Resource(Coordinates coordinates, ResourceState resourceState, Optional<Person> person) {
        this.coordinates = coordinates;
        this.resourceState = resourceState;
        this.person = person;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public ResourceState getResourceState() {
        return resourceState;
    }

    public void setResourceState(ResourceState resourceState) {
        this.resourceState = resourceState;
    }

    public Optional<Person> getPerson() {
        return person;
    }

    public void setPerson(Optional<Person> person) {
        this.person = person;
    }
}