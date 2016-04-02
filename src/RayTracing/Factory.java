package RayTracing;


import java.util.List;

public class Factory {

    public static Camera createCamera(String[] params, int imageHeight, int imageWidth) {

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

    public static Material createMaterial(String[] params) {
        float dr, dg, db, sr, sg, sb, rr, rg, rb;
        Color defuseColor, specularColor, reflection;

        float phongCoefficient = Float.parseFloat(params[9]);
        float transparency = Float.parseFloat(params[10]);

        dr = Float.parseFloat(params[0]);
        dg = Float.parseFloat(params[1]);
        db = Float.parseFloat(params[2]);
        sr = Float.parseFloat(params[3]);
        sg = Float.parseFloat(params[4]);
        sb = Float.parseFloat(params[5]);
        rr = Float.parseFloat(params[6]);
        rg = Float.parseFloat(params[7]);
        rb = Float.parseFloat(params[8]);

        defuseColor = new Color(dr, dg, db);
        specularColor = new Color(sr, sg, sb);
        reflection = new Color(rr, rg, rb);

        Material material = new Material(defuseColor, specularColor, phongCoefficient, reflection, transparency );
        return  material;
    }

    public static Surface createSphere(String[] params, List<Material> materialsList) {
        Sphere sphere

    }
}
