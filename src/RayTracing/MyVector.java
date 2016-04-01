package RayTracing;

public class MyVector {

    private int x;
    private int y;
    private int z;

    public MyVector(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int get_x() {
        return this.x;
    }

    public int get_y() {
        return this.y;
    }

    public int get_z() {
        return this.z;
    }

    public void set_x(int x) {
        this.x = x;
    }

    public void set_y(int y) {
        this.y = y;
    }

    public void set_z(int z) {
        this.z = z;
    }


}
