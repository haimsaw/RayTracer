package RayTracing;

public class Intersection {
    public Surface surface;
    public MyVector position;
    public MyVector directionToRayStart;

    public MyVector getDirectionToRayStart() {
        return directionToRayStart;
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
        this.directionToRayStart = rayDirection.multiply(-1);
    }




}
