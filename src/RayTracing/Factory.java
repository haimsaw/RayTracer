package RayTracing;


public class Factory {

public static Camera cameraCreator(String[] params, int imageHeight, int imageWidth) {

    float px, py, pz, lx, ly, lz, ux, uy, uz, screenDistance, screenWidth, screenHeight;
    px = Float.parseFloat(params[0]);
    py = Float.parseFloat(params[1]);
    pz = Float.parseFloat(params[2]);
    lx = Float.parseFloat(params[3]);
    ly = Float.parseFloat(params[4]);
    lz = Float.parseFloat(params[5]);
    ux = Float.parseFloat(params[6]);
    uy = Float.parseFloat(params[7]);
    uz = Float.parseFloat(params[8]);

    MyVector position = new MyVector(px, py, pz);
    MyVector lookAt = new MyVector(lx, ly, lz);
    MyVector up = new MyVector(ux, uy, uz);

    screenDistance = Float.parseFloat(params[9]);
    screenWidth = Float.parseFloat(params[10]);
    float proportion = imageHeight / imageWidth;
    screenHeight = proportion * screenWidth; //TODO make sure


    Camera camera = new Camera(position, lookAt, up, screenDistance, screenWidth, screenHeight);
    return camera;
    }

}
