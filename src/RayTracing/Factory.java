package RayTracing;


import java.util.List;

class Factory {

    public static Camera createCamera(String[] params, int imageHeight, int imageWidth) {

        double px, py, pz, lx, ly, lz, ux, uy, uz, screenDistance, screenWidth, screenHeight;
        px = Double.parseDouble(params[0]);
        py = Double.parseDouble(params[1]);
        pz = Double.parseDouble(params[2]);
        lx = Double.parseDouble(params[3]);
        ly = Double.parseDouble(params[4]);
        lz = Double.parseDouble(params[5]);
        ux = Double.parseDouble(params[6]);
        uy = Double.parseDouble(params[7]);
        uz = Double.parseDouble(params[8]);

        MyVector position = new MyVector(px, py, pz);
        MyVector lookAt = new MyVector(lx, ly, lz).subtract(position);
        MyVector up = new MyVector(ux, uy, uz);

        screenDistance = Double.parseDouble(params[9]);
        screenWidth = Double.parseDouble(params[10]);
        double proportion = (double) imageHeight / (double) imageWidth;
        screenHeight = proportion * screenWidth; //TODO make sure


        Camera camera = new Camera(position, lookAt, up, screenDistance, screenWidth, screenHeight);
        return camera;
    }

    static Material createMaterial(String[] params) {
        double dr, dg, db, sr, sg, sb, rr, rg, rb;
        Color defuseColor, specularColor, reflection;
        Material material;

        double phongCoefficient = Double.parseDouble(params[9]);
        double transparency = Double.parseDouble(params[10]);

        dr = Double.parseDouble(params[0]);
        dg = Double.parseDouble(params[1]);
        db = Double.parseDouble(params[2]);
        sr = Double.parseDouble(params[3]);
        sg = Double.parseDouble(params[4]);
        sb = Double.parseDouble(params[5]);
        rr = Double.parseDouble(params[6]);
        rg = Double.parseDouble(params[7]);
        rb = Double.parseDouble(params[8]);

        defuseColor = new Color(dr, dg, db);
        specularColor = new Color(sr, sg, sb);
        reflection = new Color(rr, rg, rb);

        material = new Material(defuseColor, specularColor, phongCoefficient, reflection, transparency);
        return material;
    }

    static Sphere createSphere(String[] params, List<Material> materialsList) {
        Sphere sphere;
        double centerX, centerY, centerZ, radious;
        int matIndex;

        centerX = Double.parseDouble(params[0]);
        centerY = Double.parseDouble(params[1]);
        centerZ = Double.parseDouble(params[2]);
        matIndex = Integer.parseInt(params[4]);
        radious = Double.parseDouble(params[3]);

        Material material = materialsList.get(matIndex-1);
        MyVector center = new MyVector(centerX, centerY, centerZ);

        sphere = new Sphere(center, radious, material);
        return sphere;

    }

    static Plane createPlane(String[] params, List<Material> materialsList) {
        Plane plane;
        double normalX, normalY, normalZ, offset;
        int matIndx;

        normalX = Double.parseDouble(params[0]);
        normalY = Double.parseDouble(params[1]);
        normalZ = Double.parseDouble(params[2]);
        offset = Double.parseDouble(params[3]);
        matIndx = Integer.parseInt(params[4]);

        MyVector normal = new MyVector(normalX, normalY, normalZ);
        Material material = materialsList.get(matIndx- 1);

        plane = new Plane(normal, offset, material);
        return plane;
    }

    static Cylinder createCylinder(String[] params, List<Material> materialList) {
        Cylinder cylinder;
        double centerX, centerY, centerZ, length, radius, rotationX, rotationY, rotationZ;
        MyVector centerPosition;
        double[] rotation;
        int matIndex;
        Material material;

        centerX = Double.parseDouble(params[0]);
        centerY = Double.parseDouble(params[1]);
        centerZ = Double.parseDouble(params[2]);
        length = Double.parseDouble(params[3]);
        radius = Double.parseDouble(params[4]);
        rotationX = Double.parseDouble(params[5]);
        rotationY = Double.parseDouble(params[6]);
        rotationZ = Double.parseDouble(params[7]);
        matIndex = Integer.parseInt(params[8]);

        centerPosition = new MyVector(centerX, centerY, centerZ);
        rotation = new double[] {rotationX, rotationY, rotationZ};
        material = materialList.get(matIndex - 1);

        cylinder = new Cylinder(centerPosition, length, radius, rotation, material);
        return cylinder;
    }

    static Light createLight(String[] params) {
        Light light;
        MyVector position;
        Color color;
        Double specular, shadow, width, px, py, pz, r, g, b;

        px = Double.parseDouble(params[0]);
        py = Double.parseDouble(params[1]);
        pz = Double.parseDouble(params[2]);
        r = Double.parseDouble(params[3]);
        g = Double.parseDouble(params[4]);
        b = Double.parseDouble(params[5]);
        specular = Double.parseDouble(params[6]);
        shadow = Double.parseDouble(params[7]);
        width = Double.parseDouble(params[8]);

        position = new MyVector(px, py, pz);
        color = new Color(r,g,b);

        light = new Light(position, color, specular, shadow, width);
        return light;
    }
}
