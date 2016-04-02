package RayTracing;


public class Ray {
    MyVector direction;
    MyVector startPoint;

    public Ray(MyVector startPoint, MyVector endPoint) {
        this.direction = endPoint.subtract(startPoint);
        this.startPoint = startPoint;
    }

}
