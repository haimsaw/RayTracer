package RayTracing;

public class Light {
    public MyVector position;
    public Color color;
    public float specular;
    public float shadow;

    public Light(MyVector position, Color color, float specular, float shadow) {
        this.position = position;
        this.color = color;
        this.specular = specular;
        this.shadow = shadow;
    }
}
