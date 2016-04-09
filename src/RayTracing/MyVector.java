package RayTracing;

public class MyVector {

    private float x;
    private float y;
    private float z;

    public MyVector(float x, float y, float z) {
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
        float x = this.get_y()*other.get_z() - this.get_z()*other.get_y();
        float y = this.get_z()*other.get_x() - this.get_x()*other.get_z();
        float z = this.get_x()*other.get_y() - this.get_y()*other.get_x();
        return new MyVector(x, y, z);
    }

    public MyVector multiply(float scalar){
        return new MyVector(this.x*scalar, this.y*scalar, this.z*scalar);
    }

    public MyVector add(MyVector other) {
        float x = this.get_x() + other.get_x();
        float y = this.get_y() + other.get_y();
        float z = this.get_z() + other.get_z();
        return new MyVector(x, y, z);
    }

    public MyVector subtract(MyVector other){
        float x = this.get_x() - other.get_x();
        float y = this.get_y() - other.get_y();
        float z = this.get_z() - other.get_z();
        return new MyVector(x, y, z);
    }

    public float distance(MyVector other){
        MyVector tmp = this.subtract(other);
        // removed sqrt for time saving
        return this.subtract(other).getLength();
    }

    public float dotProduct(MyVector other){
        return this.get_x()*other.get_x() + this.get_y()*other.get_y() + this.get_z()*other.get_z();
    }

    public MyVector projectTo(MyVector other){
        return other.multiply(this.dotProduct(other));
    }

    public float getLength(){
        return (float) Math.sqrt((float) this.dotProduct(this));
    }

    public MyVector getNormalizedVector(){
        float len = this.getLength();
        return this.multiply(1/len);
    }

    public Matrix toMatrix() {
        float[][] array = new float[3][1];
        array[0][0] = this.x;
        array[1][0] = this.y;
        array[2][0] = this.z;
        return new Matrix(array);
    }

    public float getAbsCosAngel(MyVector other){
        return Math.abs(this.getNormalizedVector().dotProduct(other.getNormalizedVector()));
    }
    //</editor-fold>

    public float get_x() {
        return this.x;
    }

    public float get_y() {
        return this.y;
    }

    public float get_z() {
        return this.z;
    }
    /**
    public void set_x(int x) {
        this.x = x;
    }

    public void set_y(int y) {
        this.y = y;
    }

    public void set_z(int z) {
        this.z = z;
    }
    **/


}
