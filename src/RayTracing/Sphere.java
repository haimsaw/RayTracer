package RayTracing;


import java.util.LinkedList;
import java.util.List;

public class Sphere extends Surface {

    private double radius;
    private MyVector position;

    public Sphere(MyVector position, double radius, Material material) {
        super(material);
        this.position = position;
        this.radius = radius;
    }

    //<editor-fold desc="intersection">
    @Override
    public List<Intersection> getIntersections(Ray ray) {
        List<Intersection> intersections = new LinkedList<>();
        double[] coefficients =  getCoefficients(ray.getDirection(), ray.getStartPoint().subtract(position));
        List<Double> solutions = getSolutions(coefficients);
        for (double solution:solutions) {
            intersections.add(getIntersectionFromSoulution(solution, ray));
        }
        return intersections;
    }

    private double[] getCoefficients(MyVector direction, MyVector tmp){
        double degree2 = direction.dotProduct(direction);
        double degree1 = 2 * direction.dotProduct(tmp);
        double degree0 = tmp.dotProduct(tmp) - radius*radius;
        double[] ret = {degree2, degree1, degree0};
        return ret;
    }

    private List<Double> getSolutions(double[] coeffisents) {
        List<Double> solutions = new LinkedList<>();
        double a = coeffisents[0];
        double b = coeffisents[1];
        double c = coeffisents[2];
        double discriminant =b * b - 4 * a * c;
        if (discriminant >= 0){
            double solution =  (double) (-b + Math.sqrt(discriminant)) / 2  ;
            if (solution >= 0) {
                    solutions.add(solution);
                }
        }
        if (discriminant > 0) {
            double solution = (double) (-b + (Math.sqrt(discriminant)) / 2);
            if (solution >= 0) {
                solutions.add(solution);
            }
        }
        return solutions;
    }

    private Intersection getIntersectionFromSoulution(double lambda, Ray ray){
        MyVector position = ray.getPointFromLambda(lambda);
        return new Intersection(this, position, ray.getDirection());

    }
    //</editor-fold>
    @Override
    public MyVector get_normal(MyVector point) {
        return new MyVector(position, point);
    }
}
