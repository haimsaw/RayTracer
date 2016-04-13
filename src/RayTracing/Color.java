package RayTracing;

public class Color {

    private double red;
    private double green;
    private double blue;

    //<editor-fold desc="getters">
    public double getRed() {
        return red;
    }

    public double getGreen() {
        return green;
    }

    public double getBlue() {
        return blue;
    }
    //</editor-fold>

    public Color(double red, double green, double blue) {
        // todo is double??
        this.red = red>1 ? 1 : red;
        this.green = green>1 ? 1 : green;
        this.blue = blue>1 ? 1 : blue;
    }
    /**
    public Color multiply(double v) {
        return this.multiply((double) v);
    }**/

    public Color multiply(Color coefficients){
        double red = coefficients.red*this.red;
        double green = coefficients.green*this.green;
        double blue = coefficients.blue*this.blue;

        return new Color(red, green, blue);
    }

    public Color multiply(double coefficient){
        return this.multiply(new Color(coefficient, coefficient, coefficient));
    }

    public Color add(Color other){
        double red = this.getRed() + other.getRed();
        double green = this.getGreen() + other.getGreen();
        double blue = this.getBlue() + other.getBlue();
        return new Color(red, green, blue);

    }

   public String toSrtring(){
       return String.format("%f, %f, %f", red, green, blue);
   }

    public boolean isZero(){
        return red==0 && blue==0 && green==0;
    }
}
