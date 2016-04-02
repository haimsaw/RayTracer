package RayTracing;

import java.util.List;

public class Plane extends Surface {

    MyVector normal;
    float offset;

    public Plane(MyVector normal, float offset, Material material) {
        super(material);
        this.normal = normal;
        this.offset = offset;
    }

    @Override
    public List<Intersection> get_intersections(Ray ray) {
        return null;
    }

    @Override
    public MyVector get_normal(MyVector point) {
        return null;
    }
}
