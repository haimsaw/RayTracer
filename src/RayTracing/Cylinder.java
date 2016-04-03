package RayTracing;


import java.util.List;

public class Cylinder extends Surface {

    MyVector centerPosition, rotation;
    float length, radius;

    public Cylinder(MyVector centerPosition, float length, float radius, MyVector rotation, Material material){
        super(material);
        this.centerPosition = centerPosition;
        this.length = length;
        this.radius = radius;
        this.rotation = rotation;
    }

    @Override
    public List<Intersection> getIntersections(Ray ray) {
        return null;
    }

    @Override
    public MyVector get_normal(MyVector point) {
        return null;
    }
}
