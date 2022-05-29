package objects;

import java.util.Optional;

public class ResourceGrid {

    public Resource[][] resourceGridOfResourceObjects;

    public ResourceGrid(int width, int length) {
        this.resourceGridOfResourceObjects = new Resource[width][length];
    }

    public static void resetResourceGridOfResourceObjects(int width, int length, Resource[][] resourceGridOfResourceObjects) {
        for (int j = 0; j < width; j++) {
            for (int k = 0; k < length; k++) {
                resourceGridOfResourceObjects[j][k] = new Resource(new Coordinates(j, k), ResourceState.free, Optional.empty());
            }
        }
    }

    public Resource[][] getResourceGridOfResourceObjects() {
        return resourceGridOfResourceObjects;
    }

    public void setResourceGridOfResourceObjects(Resource[][] resourceGridOfResourceObjects) {
        this.resourceGridOfResourceObjects = resourceGridOfResourceObjects;
    }
}
