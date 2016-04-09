package RayTracing;

import java.util.List;
import java.util.Random;


public class ColorCalculator {
    private Intersection intersection;
    private int numOfShadowRays;
    public List<Surface> surfaces;

    public ColorCalculator(Intersection intersection, int numOfShadowRays, List<Surface> surfaces) {
        this.intersection = intersection;
        surfaces = surfaces;
    }

    public Color getColor(List<Light> lights){
        Color color = new Color(0,0,0);
        for (Light light : lights){
            color = color.add(this.getColorForLight(light));
        }
        return color;
    }

    private Color getColorForLight(Light light) {
        Color color = getBasicColor(light);
        return color.multiply(getShadowCoeff(light));

    }

    private double getShadowCoeff(Light light) {
        MyVector toIntersection = new MyVector(light.position, intersection.position);
        double planeOffset = toIntersection.dotProduct(light.position);
        MyVector planeVector1 = getPlaneVector(toIntersection, planeOffset).getNormalizedVector();
        MyVector planeVector2 = toIntersection.crossProduct(planeVector1);
        Random r = new Random();
        int numOfHits = 0;
        for (int i = 0; i<numOfShadowRays; i++){
            double coeff1 = r.nextDouble() - .5;
            double coeff2 = r.nextDouble() - .5;
            MyVector rayStart = light.position.add(planeVector1.multiply(coeff1)).add(planeVector2.multiply(coeff2));
            Ray ray = new Ray(rayStart, intersection.position);
            if (ray.getClosestIntersection(surfaces).surface == intersection.surface){
                numOfHits++;
            }
        }
        return numOfHits/numOfShadowRays;
    }

    private MyVector getPlaneVector(MyVector normal, double offset){
        double x=0,y=0,z=0;

        if (normal.get_z() != 0) {
            x = normal.get_x() + 1;
            y = normal.get_y() + 1;
            z = (offset - x * normal.get_y() - y * normal.get_y()) / normal.get_z();
        }
        else {
            //TODO
        }
        return new MyVector(x,y,z);
    }


    private Color getBasicColor(Light light) {
        Color color = new Color(0,0,0);
        color = color.add(this.specularColor(light));
        color = color.add(this.diffuseColor(light));

        return color.multiply(1-intersection.surface.material.transparency);
    }

    private Color specularColor(Light light){
        MyVector directionToLight = new MyVector(intersection.position, light.position);
        MyVector normal = intersection.surface.get_normal(intersection.position);
        MyVector reflectionDirection =(directionToLight.multiply(2).projectTo(normal)).subtract(directionToLight);
        double intensity = light.specularIntensity*
                Math.pow(reflectionDirection.getAbsCosAngel(intersection.direction), intersection.surface.material.phong_coefficient);
        return intersection.surface.material.specular_color.multiply(light.color).multiply(intensity);
    }

    private Color diffuseColor(Light light){
        MyVector directionToLight = new MyVector(intersection.position, light.position);
        double intensity = intersection.surface.get_normal(intersection.position).getAbsCosAngel(directionToLight);
        return light.color.multiply(intersection.surface.material.defuse_color).multiply(intensity);
    }


}
