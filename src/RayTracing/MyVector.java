package RayTracing;

public class MyVector {

    private double x;
    private double y;
    private double z;

    public MyVector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public MyVector(MyVector start, MyVector end){
        this.x = end.get_x() - start.get_x();
        this.y = end.get_y() - start.get_y();
        this.z = end.get_z() - start.get_z();
    }

    //<editor-fold desc="vectors math">
    public MyVector crossProduct(MyVector other){
        double x = this.get_y()*other.get_z() - this.get_z()*other.get_y();
        double y = this.get_z()*other.get_x() - this.get_x()*other.get_z();
        double z = this.get_x()*other.get_y() - this.get_y()*other.get_x();
        return new MyVector(x, y, z);
    }

    public MyVector multiply(double scalar){
        return new MyVector(this.x*scalar, this.y*scalar, this.z*scalar);
    }

    public MyVector add(MyVector other) {
        double x = this.get_x() + other.get_x();
        double y = this.get_y() + other.get_y();
        double z = this.get_z() + other.get_z();
        return new MyVector(x, y, z);
    }

    public MyVector subtract(MyVector other){
        double x = this.get_x() - other.get_x();
        double y = this.get_y() - other.get_y();
        double z = this.get_z() - other.get_z();
        return new MyVector(x, y, z);
    }

    public double distance(MyVector other){
        MyVector tmp = this.subtract(other);
        return this.subtract(other).getLength();
    }

    public double dotProduct(MyVector other){
        return this.get_x()*other.get_x() + this.get_y()*other.get_y() + this.get_z()*other.get_z();
    }

    public MyVector projectTo(MyVector other){
        MyVector normalized = other.getNormalizedVector();
        return normalized.multiply(this.dotProduct(normalized));
    }

    public double getLength(){
        return Math.sqrt( this.dotProduct(this));
    }

    public MyVector getNormalizedVector(){
        double len = this.getLength();
        return this.multiply(1/len);
    }

    public Matrix toMatrix() {
        double[][] array = new double[3][1];
        array[0][0] = this.x;
        array[1][0] = this.y;
        array[2][0] = this.z;
        return new Matrix(array);
    }

    public double getZeroOrCosAngel(MyVector other){
        double cosnAngle = this.getCosAngel(other);
        return cosnAngle > 0 ? cosnAngle : 0;
    }

    public double getCosAngel(MyVector other){
        return this.getNormalizedVector().dotProduct(other.getNormalizedVector());

    }


    public double getAbsCosAngel(MyVector other){
        return Math.abs(this.getCosAngel(other));

    }

    //</editor-fold>

    public double get_x() {
        return this.x;
    }

    public double get_y() {
        return this.y;
    }

    public double get_z() {
        return this.z;
    }

    public String toString(){
        return String.format("%f, %f, %f",x,y,z);
    }

}
