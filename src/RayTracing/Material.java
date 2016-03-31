
public class Material {

    public Color defuse_color;
    public Color specular_color;
    public float phong_coefficient;
    public Color reflection;
    public float transparency;


    public Material(Color defuse_color, Color specular_color, float phong_coefficient, Color reflection, float transparency) {
        this.defuse_color = defuse_color;
        this.specular_color = specular_color;
        this.phong_coefficient =  phong_coefficient;
        this.reflection = reflection;
        this.transparency = transparency;

    }

}
