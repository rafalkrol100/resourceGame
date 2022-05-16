package objects;

import java.util.Optional;

public class Resource {

    public static boolean isAnyResourceFree(Resource[][] resourceGridState, int width, int length) {
        for (int i = 0; i < width; i++) {
            for (int k = 0; k < length; k++) {
                if (resourceGridState[i][k].resourceState == ResourceState.free)
                    return true;
            }
        }
        return false;
    }

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
