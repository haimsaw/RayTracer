package RayTracing;


import java.util.LinkedList;
import java.util.List;

public class Cylinder extends Rounded {


    private MyVector axis;
    private double length;

    public Cylinder(MyVector position, double length, double radius, double[] rotation, Material material){
        super(material,radius,position);

        this.length = length;
        MyVector rotatedVector = Matrix.createRotationMatrix(rotation).multiplyByVector(new MyVector(0,0,1));
        this.axis = rotatedVector.getNormalizedVector();
    }

    @Override
    public MyVector get_normal(MyVector point) {
        if (isPointOnHead(point)){
            return axis;
        }
        MyVector tmp = point.subtract(centerPosition);
        return tmp.subtract(tmp.projectTo(axis));
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
        MyVector lambdaCoeff = ray.getDirection().subtract(ray.getDirection().projectTo(axis));
        MyVector tmp = ray.getStartPoint().subtract(centerPosition);
        MyVector otherCoeff = tmp.subtract(tmp.projectTo(axis));
        double[] coefficients =  getCoefficients(lambdaCoeff, otherCoeff);
        List<Double> solutions = getSolutions(coefficients);
        //if (solutions.size()>0)
        //    System.out.println(solutions);
        for (double solution:solutions) {
            if (isSolutionValid(solution, ray)) {
                intersections.add(getIntersectionFromSoulution(solution, ray));
            }
        }
    }

    private boolean isSolutionValid(double solution,Ray ray) {
        MyVector tmp = (ray.getPointFromLambda(solution).subtract(centerPosition)).projectTo(axis);
        double alpha = tmp.getLength();
        //System.out.println(tmp);

        return Math.abs(alpha) <= length/2;
    }

    private void addHeadIntersections(Ray ray, List<Intersection> intersections) {
        double upperLambda = (length/2 - ray.getStartPoint().dotProduct(axis))/ray.getDirection().dotProduct(axis);
        double lowerLambda = (-length/2 - ray.getStartPoint().dotProduct(axis))/ray.getDirection().dotProduct(axis);
        handelHeadLambda(upperLambda, true, intersections, ray);
        handelHeadLambda(lowerLambda, false, intersections, ray);

    }

    private void handelHeadLambda(double lambda, boolean isUpper, List<Intersection> intersections, Ray ray) {
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

    private boolean isPointOnHead(MyVector point){
        return isPointOnHead(point, true) || isPointOnHead(point, false);
    }
    //</editor-fold>
}


