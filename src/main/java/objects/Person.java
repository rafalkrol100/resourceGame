package objects;

public class Person {
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

    public enum Type {
        neutral,
        aggressive
    }
}
