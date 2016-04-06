package RayTracing;

import java.util.List;

public class Intersection {
    public Surface surface;
    public MyVector position;
    public MyVector direction;

    public MyVector getDirection() {
        return direction;
    }

    public Surface getSurface() {
        return surface;
    }

    public MyVector getPosition() {
        return position;
    }

    /**
     *
     * @param surface
     * @param position assumes position is on the surface!!!!
     * @param rayDirection
     */
    public Intersection(Surface surface, MyVector position, MyVector rayDirection) {
        this.surface = surface;
        this.position = position;
        this.direction = rayDirection.multiply(-1);
    }




}
