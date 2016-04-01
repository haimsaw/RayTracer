package RayTracing;

class Camera {
    public MyVector position;
    public MyVector look_at;
    public MyVector up;
    public float screen_distance;
    public float screen_width;
    public float screen_hight;

    public Camera(MyVector position, MyVector look_at, MyVector up, float screen_distance, float screen_width, float screen_hight) {
        this.position = position;
        this.look_at = look_at;
        this.up = up;
        this.screen_distance = screen_distance;
        this.screen_width = screen_width;
        this.screen_hight = screen_hight;
    }
}
