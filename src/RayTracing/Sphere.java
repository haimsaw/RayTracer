package RayTracing;


import java.util.List;

public class Sphere extends Surface {

    float radius;
    MyVector position;

    public Sphere(MyVector position, float radius, Material material) {
        super(material);
        this.position = position;
        this.radius = radius;
    }

    @Override
    public List<Intersection> get_intersections(Ray ray) {
        return null;
    }

    @Override
    public MyVector get_normal(MyVector point) {
        return new MyVector(position, point);
    }
}
