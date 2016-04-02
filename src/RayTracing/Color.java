package RayTracing;

public class Color {

    float red;
    float green;
    float blue;

    public Color(float red, float green, float blue) {
        this.red = red>1 ? 1 : red;
        this.green = green>1 ? 1 : green;
        this.blue = blue>1 ? 1 : blue;
    }

    public Color multiply(float coefficient){
        float red = coefficient*this.red;
        float green = coefficient*this.green;
        float blue = coefficient*this.blue;

        return new Color(red, green, blue);
    }

}
