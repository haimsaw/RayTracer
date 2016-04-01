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
