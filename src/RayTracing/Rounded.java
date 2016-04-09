package RayTracing;

import RayTracing.Intersection;
import RayTracing.Material;
import RayTracing.MyVector;
import RayTracing.Ray;

import java.util.LinkedList;
import java.util.List;

public abstract class Rounded  extends Surface{
    protected double radius;
    protected MyVector centerPosition;

    public Rounded(Material material, double radius, MyVector centerPosition) {
        super(material);
        this.radius = radius;
        this.centerPosition = centerPosition;
    }

    protected double[] getCoefficients(MyVector lambdaCoeff, MyVector other){
        double degree2 = lambdaCoeff.dotProduct(lambdaCoeff);
        double degree1 = 2 * lambdaCoeff.dotProduct(other);
        double degree0 = other.dotProduct(other) - radius*radius;
        double[] ret = {degree2, degree1, degree0};
        return ret;
    }

    protected List<Double> getSolutions(double[] coeffisents) {
        List<Double> solutions = new LinkedList<>();
        double a = coeffisents[0];
        double b = coeffisents[1];
        double c = coeffisents[2];
        double discriminant =b * b - 4 * a * c;
        if (discriminant >= 0){
            double solution =  (-b + Math.sqrt(discriminant)) / 2  ;
            if (solution >= 0) {
                solutions.add(solution);
            }
        }
        if (discriminant > 0) {
            double solution = (-b + Math.sqrt(discriminant)) / 2;
            if (solution >= 0) {
                solutions.add(solution);
            }
        }
        return solutions;
    }

    protected Intersection getIntersectionFromSoulution(double lambda, Ray ray){
        MyVector position = ray.getPointFromLambda(lambda);
        return new Intersection(this, position, ray.getDirection());

    }


}