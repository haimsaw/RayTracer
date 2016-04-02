package RayTracing;


public class Intersection {
    private Surface surface;
    private MyVector position;

    public Intersection(Surface surface, MyVector position) {
        this.surface = surface;
        this.position = position;
    }

    public Surface getSurface() {
        return surface;
    }

    public MyVector getPosition() {
        return position;
    }

    public Color getColor(){

    }

}
