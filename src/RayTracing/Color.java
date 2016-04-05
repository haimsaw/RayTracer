package RayTracing;

public class Color {

    private float red;
    private float green;
    private float blue;

    //<editor-fold desc="getters">
    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }
    //</editor-fold>

    public Color(float red, float green, float blue) {
        // todo is float??
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

    public Color add(Color other){
        float red = this.getRed() + other.getRed();
        float green = this.getGreen() + other.getGreen();
        float blue = this.getBlue() + other.getBlue();
        return new Color(red, green, blue);

    }

    public Color multiply(double v) {
        return this.multiply((float) v);
    }
}
