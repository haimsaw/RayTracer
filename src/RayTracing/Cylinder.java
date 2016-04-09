package RayTracing;


import java.util.LinkedList;
import java.util.List;

public class Cylinder extends Surface {

    private MyVector centerPosition;
    private MyVector axis;
    private float length, radius;

    public Cylinder(MyVector centerPosition, float length, float radius, float[] rotation, Material material){
        super(material);
        this.centerPosition = centerPosition;
        this.length = length;
        this.radius = radius;
        this.axis = Matrix.createRotationMatrix(rotation).multiplyByVector(new MyVector(0,0,1)).getNormalizedVector();
    }

    @Override
    public MyVector get_normal(MyVector point) {
        // todo
        return axis;
    }


    //<editor-fold desc="intersection">
    @Override
    public List<Intersection> getIntersections(Ray ray) {
        List<Intersection> intersections = new LinkedList<>();
        addHeadIntersections(ray, intersections);
        addBodyIntersections(ray, intersections);
        return  intersections;
    }

    private void addBodyIntersections(Ray ray, List<Intersection> intersections) {

    }

    private void addHeadIntersections(Ray ray, List<Intersection> intersections) {
        float upperLambda = (length/2 - ray.getStartPoint().dotProduct(axis))/ray.getDirection().dotProduct(axis);
        float lowerLambda = (-length/2 - ray.getStartPoint().dotProduct(axis))/ray.getDirection().dotProduct(axis);
        handelHeadLambda(upperLambda, true, intersections, ray);
        handelHeadLambda(lowerLambda, false, intersections, ray);

    }

    private void handelHeadLambda(float lambda, boolean isUpper, List<Intersection> intersections, Ray ray) {
        if (lambda >= 0) {
            MyVector intersectionPoint = ray.getPointFromLambda(lambda);
            if (isPointOnHead(intersectionPoint, isUpper)) {
                intersections.add(new Intersection(this, intersectionPoint, ray.getDirection()));
            }
        }
    }

    private boolean isPointOnHead(MyVector point, boolean isUpper){
        MyVector center = isUpper ? centerPosition.add(axis.multiply(length/2)) : centerPosition.subtract(axis.multiply(length / 2)) ;
        return center.distance(point) <= radius ;

    }

    //</editor-fold>
}


