package RayTracing;


import java.util.LinkedList;
import java.util.List;

public class Sphere extends Rounded {

    public Sphere(MyVector position, double radius, Material material) {
        super(material,radius,position);
    }

    //<editor-fold desc="intersection">
    @Override
    public List<Intersection> getIntersections(Ray ray) {
        List<Intersection> intersections = new LinkedList<>();
        double[] coefficients =  getCoefficients(ray.getDirection(), ray.getStartPoint().subtract(centerPosition));
        List<Double> solutions = getSolutions(coefficients);
        for (double solution:solutions) {
            intersections.add(getIntersectionFromSoulution(solution, ray));
        }
        return intersections;
    }

    //</editor-fold>
    @Override
    public MyVector get_normal(MyVector point) {
        return new MyVector(centerPosition, point);
    }
}
