package RayTracing;


import java.util.LinkedList;
import java.util.List;

public class Sphere extends Surface {

    private float radius;
    private MyVector position;

    public Sphere(MyVector position, float radius, Material material) {
        super(material);
        this.position = position;
        this.radius = radius;
    }

    //<editor-fold desc="intersection">
    @Override
    public List<Intersection> getIntersections(Ray ray) {
        List<Intersection> intersections = new LinkedList<>();
        float[] coefficients =  getCoefficients(ray.getDirection(), ray.getStartPoint().subtract(position));
        List<Float> solutions = getSolutions(coefficients);
        for (float solution:solutions) {
            intersections.add(getIntersectionFromSoulution(solution, ray));
        }
        return intersections;
    }

    private float[] getCoefficients(MyVector direction, MyVector tmp){
        float degree2 = direction.dotProduct(direction);
        float degree1 = 2 * direction.dotProduct(tmp);
        float degree0 = tmp.dotProduct(tmp) - radius*radius;
        float[] ret = {degree2, degree1, degree0};
        return ret;
    }

    private List<Float> getSolutions(float[] coeffisents) {
        List<Float> solutions = new LinkedList<>();
        float a = coeffisents[0];
        float b = coeffisents[1];
        float c = coeffisents[2];
        float discriminant =b * b - 4 * a * c;
        if (discriminant >= 0){
            //todo not negative
            float solution =  (float) (-b + Math.sqrt(discriminant)) / 2  ;
            if (solution >= 0) {
                    solutions.add(solution);
                }
        }
        if (discriminant > 0) {
            float solution = (float) (-b + (Math.sqrt(discriminant)) / 2);
            if (solution >= 0) {
                solutions.add(solution);
            }
        }
        return solutions;
    }

    private Intersection getIntersectionFromSoulution(float lambda, Ray ray){
        MyVector position = ray.getPointFromLambda(lambda);
        return new Intersection(this, position, ray.getDirection());

    }
    //</editor-fold>
    @Override
    public MyVector get_normal(MyVector point) {
        return new MyVector(position, point);
    }
}
