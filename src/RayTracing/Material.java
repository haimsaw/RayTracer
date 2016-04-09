package RayTracing;

public class Material {

    public Color defuse_color;
    public Color specular_color;
    public double phong_coefficient;
    public Color reflection;
    public double transparency;


    public Material(Color defuse_color, Color specular_color, double phong_coefficient, Color reflection, double transparency) {
        this.defuse_color = defuse_color;
        this.specular_color = specular_color;
        this.phong_coefficient =  phong_coefficient;
        this.reflection = reflection;
        this.transparency = transparency;

    }

}
