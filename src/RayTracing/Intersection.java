package RayTracing;


public class Intersection {
    private Surface surface;
    private MyVector position;

    public Intersection(Surface surface, MyVector position) {
        this.surface = surface;
        this.position = position;
    }

    public Surface getSurface() {
        return surface;
    }

    public MyVector getPosition() {
        return position;
    }

    //<editor-fold desc="colors parts">
    private Color AmbiantColor(Light light){
        // todo need to compute?
        return new Color(0,0,0);
    }
    private Color diffuseColor(Light light){
        MyVector directionToLight = new MyVector(position, light.position);
        float intensity = surface.get_normal(position).dotProduct(directionToLight);
        Color diffuseColor = light.color.multiply(surface.material.defuse_color).multiply(intensity);
        return diffuseColor;
    }

    private Color specularColor(Light light){
        // todo
        return new Color(0,0,0);
    }

    //</editor-fold>

}
