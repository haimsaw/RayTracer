package RayTracing;

public class Color {

    private float red;
    private float green;
    private float blue;

    public Color(float red, float green, float blue) {
        this.red = red>1 ? 1 : red;
        this.green = green>1 ? 1 : green;
        this.blue = blue>1 ? 1 : blue;
    }

    public Color multiply(Color coefficients){
        float red = coefficients.red*this.red;
        float green = coefficients.green*this.green;
        float blue = coefficients.blue*this.blue;

        return new Color(red, green, blue);
    }

    public Color multiply(float coefficient){
        return this.multiply(new Color(coefficient, coefficient, coefficient));
    }

}
