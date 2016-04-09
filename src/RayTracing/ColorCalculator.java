package RayTracing;

import java.util.List;


public class ColorCalculator {
    private Intersection intersection;

    public ColorCalculator(Intersection intersection) {
        this.intersection = intersection;
    }

    public Color getColor(List<Light> lights){
        Color color = new Color(0,0,0);
        for (Light light : lights){
            color = color.add(this.getColorForLight(light));
        }
        return color;
    }

    private Color getColorForLight(Light light) {
        Color color = new Color(0,0,0);
        color = color.add(this.specularColor(light));
        color = color.add(this.diffuseColor(light));

        return color.multiply(1-intersection.surface.material.transparency);
    }

    private Color specularColor(Light light){
        MyVector directionToLight = new MyVector(intersection.position, light.position);
        MyVector normal = intersection.surface.get_normal(intersection.position);
        MyVector reflectionDirection =(directionToLight.multiply(2).projectTo(normal)).subtract(directionToLight);
        float intensity = light.specularIntensity*
                (float) Math.pow(reflectionDirection.getAbsCosAngel(intersection.direction), intersection.surface.material.phong_coefficient);
        return intersection.surface.material.specular_color.multiply(light.color).multiply(intensity);
    }

    private Color diffuseColor(Light light){
        MyVector directionToLight = new MyVector(intersection.position, light.position);
        float intensity = intersection.surface.get_normal(intersection.position).getAbsCosAngel(directionToLight);
        return light.color.multiply(intersection.surface.material.defuse_color).multiply(intensity);
    }


}
