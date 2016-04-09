package RayTracing;

import java.util.LinkedList;
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
    public List<Intersection> getIntersections(Ray ray) {
        LinkedList<Intersection> intersections = new LinkedList<Intersection>();
        /**
         * intersection - ray.start = lambda*ray.direction
         * intersection dot this.normal = this.offset
         *
         * ->
         * lambda = (this.offset - ray.start dot this.normal)/ray.diration dot this.normal
         * intersection = lambda*ray.direction + ray.start
         */

        float lambda = (this.offset - ray.getStartPoint().dotProduct(normal))/(ray.getDirection().dotProduct(normal));
        if (lambda<0){
            return intersections;
        }
        MyVector intersectionPoint = ray.getPointFromLambda(lambda);
        Intersection intersection = new Intersection(this,intersectionPoint, ray.getDirection());
        intersections.add(intersection);
        return intersections;
    }

    @Override
    public MyVector get_normal(MyVector point) {
        return normal;
    }
}
