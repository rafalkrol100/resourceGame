package objects;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class Simulation {
    public static void runSimulation(int width, int length, Resource[][] resourceGridOfResourceObjects, int numberOfRepetitions, boolean writeObject) {
        for (int i = 0; i < numberOfRepetitions; i++) {
            ResourceGrid.resetResourceGridOfResourceObjects(width, length, resourceGridOfResourceObjects);
            Person.personsList.forEach(person -> person.setResourceAmount(0));
            for (Person person : Person.personsList) {
                Person.dailyRoutine(person, width, length, resourceGridOfResourceObjects);
            }


            int countAggressive = (int) Person.personsList.stream().filter(person -> person.getType().equals(Person.Type.aggressive) && person.isAlive()).count();
            int countNeutral = (int) Person.personsList.stream().filter(person -> person.getType().equals(Person.Type.neutral) && person.isAlive()).count();
//            System.out.println("Number of aggressive persons during day: " + i + " is equal to: " + countAggressive);
//            System.out.println("Number of neutral persons during day: " + i + " is equal to: " + countNeutral);
            System.out.println("aggressive - " + countAggressive + " : " + countNeutral + " - neutral");


            for (Person person : Person.personsList) {
                int age = person.getAge();
                person.setAge(age + 1);
            }

            for (int k = 0; k < Person.personsList.size(); k++) {
                if (Person.personsList.get(k).getResourceAmount() == 0) {
                    Person.personsList.get(k).setAlive(false);
                }
            }

            for (int k = 0; k < Person.personsList.size(); k++) {
                if (Person.personsList.get(k).isAlive()) {
                    if (Person.personsList.get(k).getResourceAmount() == 1) {
                        Person.personsList.add(new Person(0, Person.personsList.size(), Person.personsList.get(k).getType(), 0, true, new Coordinates(0, 0)));
                    }
                }
            }


            if (writeObject) {
                Person.personsList.stream().filter(person -> person.getType().equals(Person.Type.aggressive)).collect(Collectors.toList()).forEach(System.out::println);
                Person.personsList.stream().filter(person -> person.getType().equals(Person.Type.neutral)).collect(Collectors.toList()).forEach(System.out::println);

            }
        }
    }

}
