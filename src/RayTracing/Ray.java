package RayTracing;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Ray {
    private MyVector direction;
    private MyVector startPoint;

    //<editor-fold desc="getters and setters">
    public MyVector getDirection() {
        return direction;
    }

    public MyVector getStartPoint() {
        return startPoint;
    }
    //</editor-fold>

    public Ray(MyVector startPoint, MyVector endPoint) {
        this.direction = endPoint.subtract(startPoint).getNormalizedVector();
        this.startPoint = startPoint;
    }

    Intersection getClosestIntersection(List<Surface> surfaces ) {
        //todo no intersection
        LinkedList<Intersection> intersections = new LinkedList<>();
        for (Surface surface : surfaces){
            intersections.addAll(surface.getIntersections(this));
        }
        if (intersections.isEmpty()){
            return null;
        }
        return Collections.min(intersections,
                    (o1, o2) -> {
                    double delta = o1.getPosition().distance(this.startPoint) - o2.getPosition().distance(this.startPoint);
                    if (delta < 0){
                        return  -1;
                    }
                    return 1;
                });
    }

    public MyVector getPointFromLambda(double lambda){
        return direction.multiply(lambda).add(startPoint);
    }
}
