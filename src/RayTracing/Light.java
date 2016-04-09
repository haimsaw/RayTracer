package RayTracing;

public class Light {
    public MyVector position;
    public Color color;
    public double specularIntensity;
    public double shadow;
    public double radius;

    public Light(MyVector position, Color color, double specular, double shadow, double radius) {
        this.position = position;
        this.color = color;
        this.specularIntensity = specular;
        this.shadow = shadow;
        this.radius = radius;
    }
}
