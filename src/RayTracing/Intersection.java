package RayTracing;

public class Intersection {
    private Surface surface;
    private MyVector position;
    private MyVector direction;


    /**
     *
     * @param surface
     * @param position assumes position is on the surface!!!!
     * @param rayDirection
     */
    public Intersection(Surface surface, MyVector position, MyVector rayDirection) {
        this.surface = surface;
        this.position = position;
        this.direction = rayDirection.multiply(-1);
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
        return light.color.multiply(surface.material.defuse_color).multiply(intensity);
    }

    private Color specularColor(Light light){
        MyVector directionToLight = new MyVector(position, light.position);
        MyVector normal = surface.get_normal(position);
        MyVector reflectionDirection =(directionToLight.multiply(2).projectTo(normal)).subtract(directionToLight);
        float intensity = light.specularIntensity*
                (float) Math.pow(reflectionDirection.dotProduct(direction), surface.material.phong_coefficient);
        return surface.material.specular_color.multiply(light.color).multiply(intensity);
    }

    //</editor-fold>

}
