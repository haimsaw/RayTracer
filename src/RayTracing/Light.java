package RayTracing;

public class Light {
    public MyVector position;
    public Color color;
    public float specularIntensity;
    public float shadow;
    public float radius;

    public Light(MyVector position, Color color, float specular, float shadow, float radius) {
        this.position = position;
        this.color = color;
        this.specularIntensity = specular;
        this.shadow = shadow;
        this.radius = radius;
    }
}
