package RayTracing;

class Camera {
    public MyVector position;
    public MyVector lookAt;
    public MyVector up;
    public float screen_distance;
    public float screen_width;
    public float screen_height;
    public MyVector left;
    public MyVector screenUpperLeft;

    public Camera(MyVector position, MyVector lookAt, MyVector up, float screen_distance, float screen_width, float screen_hight) {
        this.position = position;
        this.lookAt = lookAt;//.subtract(position);
        this.up = up;
        this.screen_distance = screen_distance;
        this.screen_width = screen_width;
        this.screen_height = screen_hight;
        this.left = up.crossProduct(lookAt);
        this.screenUpperLeft = this.calcScreenCoroner();
    }

    public Ray getRay(int down, int right, float screenToImageRatio){
        MyVector rightShift = this.left.multiply(-1*right*screenToImageRatio);
        MyVector downShift = this.up.multiply(-1*down*screenToImageRatio);

        return new Ray(this.position, this.screenUpperLeft.add(rightShift.add(downShift)));

    }

    private MyVector calcScreenCoroner() {
        MyVector forewordShift = this.lookAt.multiply(this.screen_distance);
        MyVector upShift = this.up.multiply(this.screen_height/2);
        MyVector leftSift = this.left.multiply(this.screen_width/2);

        return this.position.add(forewordShift.add(upShift.add(leftSift))); //position + shiftUp + shiftLeft + shiftForword

    }
}
