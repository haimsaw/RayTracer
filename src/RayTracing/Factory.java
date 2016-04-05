package RayTracing;


import java.util.List;

class Factory {

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
        MyVector lookAt = new MyVector(lx, ly, lz).subtract(position);
        MyVector up = new MyVector(ux, uy, uz);

        screenDistance = Float.parseFloat(params[9]);
        screenWidth = Float.parseFloat(params[10]);
        float proportion = (float) imageHeight / (float) imageWidth;
        screenHeight = proportion * screenWidth; //TODO make sure


        Camera camera = new Camera(position, lookAt, up, screenDistance, screenWidth, screenHeight);
        return camera;
    }

    static Material createMaterial(String[] params) {
        float dr, dg, db, sr, sg, sb, rr, rg, rb;
        Color defuseColor, specularColor, reflection;
        Material material;

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

        material = new Material(defuseColor, specularColor, phongCoefficient, reflection, transparency);
        return material;
    }

    static Sphere createSphere(String[] params, List<Material> materialsList) {
        Sphere sphere;
        float centerX, centerY, centerZ, radious;
        int matIndex;

        centerX = Float.parseFloat(params[0]);
        centerY = Float.parseFloat(params[1]);
        centerZ = Float.parseFloat(params[2]);
        matIndex = Integer.parseInt(params[4]);
        radious = Float.parseFloat(params[3]);

        Material material = materialsList.get(matIndex-1);
        MyVector center = new MyVector(centerX, centerY, centerZ);

        sphere = new Sphere(center, radious, material);
        return sphere;

    }

    static Plane createPlane(String[] params, List<Material> materialsList) {
        Plane plane;
        float normalX, normalY, normalZ, offset;
        int matIndx;

        normalX = Float.parseFloat(params[0]);
        normalY = Float.parseFloat(params[1]);
        normalZ = Float.parseFloat(params[2]);
        offset = Float.parseFloat(params[3]);
        matIndx = Integer.parseInt(params[4]);

        MyVector normal = new MyVector(normalX, normalY, normalZ);
        Material material = materialsList.get(matIndx- 1);

        plane = new Plane(normal, offset, material);
        return plane;
    }

    static Cylinder createCylinder(String[] params, List<Material> materialList) {
        Cylinder cylinder;
        float centerX, centerY, centerZ, length, radius, rotationX, rotationY, rotationZ;
        MyVector centerPosition, rotation;
        int matIndex;
        Material material;

        centerX = Float.parseFloat(params[0]);
        centerY = Float.parseFloat(params[1]);
        centerZ = Float.parseFloat(params[2]);
        length = Float.parseFloat(params[3]);
        radius = Float.parseFloat(params[4]);
        rotationX = Float.parseFloat(params[5]);
        rotationY = Float.parseFloat(params[6]);
        rotationZ = Float.parseFloat(params[7]);
        matIndex = Integer.parseInt(params[8]);

        centerPosition = new MyVector(centerX, centerY, centerZ);
        rotation = new MyVector(rotationX, rotationY, rotationZ);
        material = materialList.get(matIndex - 1);

        cylinder = new Cylinder(centerPosition, length, radius, rotation, material);
        return cylinder;
    }

    static Light createLight(String[] params) {
        Light light;
        MyVector position;
        Color color;
        Float specular, shadow, width, px, py, pz, r, g, b;

        px = Float.parseFloat(params[0]);
        py = Float.parseFloat(params[1]);
        pz = Float.parseFloat(params[2]);
        r = Float.parseFloat(params[3]);
        g = Float.parseFloat(params[4]);
        b = Float.parseFloat(params[5]);
        specular = Float.parseFloat(params[6]);
        shadow = Float.parseFloat(params[7]);
        width = Float.parseFloat(params[8]);

        position = new MyVector(px, py, pz);
        color = new Color(r,g,b);

        light = new Light(position, color, specular, shadow, width);
        return light;
    }
}
