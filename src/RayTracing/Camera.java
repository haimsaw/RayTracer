package RayTracing;

class Camera {
    public MyVector position;
    public MyVector lookAt;
    public MyVector up;
    public double screen_distance;
    public double screen_width;
    public double screen_height;
    public MyVector left;
    public MyVector screenUpperLeft;

    public Camera(MyVector position, MyVector lookAt, MyVector up, double screen_distance, double screen_width, double screen_hight) {
        this.position = position;


        this.screen_distance = screen_distance;
        this.screen_width = screen_width;
        this.screen_height = screen_hight;
        this.left = up.crossProduct(lookAt).getNormalizedVector();
        this.lookAt = lookAt.getNormalizedVector();
        this.up = lookAt.crossProduct(left).getNormalizedVector();
        this.left = left.multiply(-1);
        this.screenUpperLeft = this.calcScreenCoroner();
    }

    public Ray getRay(int down, int right, double screenToImageRatio){
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
