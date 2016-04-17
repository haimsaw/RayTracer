package RayTracing;

import java.util.List;
import java.util.Random;


public class ColorCalculator {
    private int numOfShadowRays;
    public List<Surface> surfaces;
    private double epsilon;
    Color backgroundColor;
    public List<Light> lights;
    long counter;


    public ColorCalculator(int numOfShadowRays, List<Surface> surfaces, Color backgroundColor, List<Light> lights) {
        this.surfaces = surfaces;
        this.numOfShadowRays = numOfShadowRays ;
        epsilon = 0.0001;
        this.backgroundColor =backgroundColor;
        this.lights = lights;
        counter = 0;
    }


    public Color traceRay(Ray ray, int recursionDepth) {
        Intersection intersection =  ray.getClosestIntersection(this.surfaces);
        return intersection != null ? this.getColor(lights, recursionDepth, intersection) : backgroundColor;

    }

    private Color getColor(List<Light> lights, int recursionDepth,Intersection intersection ) {
        counter ++;
        Color color = getBasicColor(lights, intersection);
        if (recursionDepth == 0 ){
            return color;
        }

        double transparencyCoeff = intersection.surface.material.transparency;
        color = color.multiply(1-transparencyCoeff);
        Color transparencyColor = getTransparencyColor(intersection, recursionDepth).multiply(transparencyCoeff);
        Color reflectionColor = getReflectionColor(intersection, recursionDepth);
        return transparencyColor.add(color).add(reflectionColor);
    }

    //<editor-fold desc="reflection trancparacy">
    private Color getReflectionColor(Intersection intersection, int recursionDepth) {
        if (intersection.surface.material.reflection.isZero() ){
            return new Color(0,0,0);
        }
        MyVector reflectionDirection = reflectionDirection(intersection.getDirectionToRayStart(),intersection);
        MyVector rayStart = intersection.position.add(reflectionDirection.multiply(epsilon));
        MyVector rayEnd = rayStart.add(reflectionDirection);
        Ray ray = new Ray(rayStart,rayEnd);
        return this.traceRay(ray, recursionDepth -1).multiply(intersection.surface.material.reflection);
    }

    private Color getTransparencyColor(Intersection intersection, int recursionDepth) {
        if (intersection.surface.material.transparency == 0){
            return new Color(0,0,0);
        }
        MyVector rayDirection = intersection.directionToRayStart.multiply(-1);
        MyVector rayStart = intersection.position.add(rayDirection.multiply(epsilon));
        MyVector rayEnd = rayStart.add(rayDirection);
        Ray ray = new Ray(rayStart,rayEnd);
        return this.traceRay(ray, recursionDepth -1);


    }

    private MyVector reflectionDirection(MyVector reflectionSrc, Intersection intersection) {
        MyVector normal = intersection.surface.getNormalToDirection(intersection.position, intersection.directionToRayStart);
        return (reflectionSrc.multiply(2).projectTo(normal)).subtract(reflectionSrc).getNormalizedVector();
    }
    //</editor-fold>

    //<editor-fold desc="shadow">
    private double getShadowCoeff(Light light, Intersection intersection) {

        MyVector toIntersection = new MyVector(light.position, intersection.position);
        double planeOffset = toIntersection.dotProduct(light.position);
        MyVector planeVector1 = getPlaneVector(toIntersection, planeOffset).getNormalizedVector().multiply(light.radius);
        MyVector planeVector2 = toIntersection.crossProduct(planeVector1).getNormalizedVector().multiply(light.radius);
        MyVector rectStart = light.position.subtract(planeVector1.multiply(0.5)).subtract(planeVector1.multiply(0.5));
        Random r = new Random();
        double numOfHits = 0;
        for (int i = 0; i<numOfShadowRays; i++) {
            for (int j = 0; j < numOfShadowRays; j++) {
                double coeff1 = r.nextDouble();
                double coeff2 = r.nextDouble();
                MyVector rayStart = rectStart.add(planeVector1.multiply((i+coeff1)/numOfShadowRays))
                        .add(planeVector2.multiply((j+coeff2)/numOfShadowRays));
                Ray ray = new Ray(rayStart, intersection.position);

                double acummelateShadow = 1;
                double distanceToLight = intersection.position.distance(rayStart) - 0.00001; //epsilon

                for (Intersection shadowRayIntesection:ray.getIntersections(surfaces)){
                    if (shadowRayIntesection.position.distance(rayStart)<distanceToLight) {
                        acummelateShadow *= shadowRayIntesection.surface.material.transparency;
                    }
                }
                numOfHits += acummelateShadow;

            }
        }
        if (intersection.surface instanceof Cylinder && numOfHits == 36){
            int i = 1;
        }
        return 1-light.shadow + light.shadow*numOfHits/(numOfShadowRays*numOfShadowRays);
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
    //</editor-fold>

    //<editor-fold desc="basic color">
    private Color getBasicColor(List<Light> lights, Intersection intersection) {
        Color color = new Color(0,0,0);
        for (Light light : lights){
            color = color.add(getBasicColorForLight(light, intersection).multiply(getShadowCoeff(light, intersection)));
        }
        return color;
    }

    private Color getBasicColorForLight(Light light, Intersection intersection) {
        Color color = new Color(0,0,0);
        color = color.add(this.specularColor(light, intersection));
        color = color.add(this.diffuseColor(light, intersection));

        return color;
    }

    private Color specularColor(Light light, Intersection intersection){
        MyVector reflectionDirection = reflectionDirection(light.position.subtract(intersection.position).getNormalizedVector(), intersection);
        double intensity = light.specularIntensity*
                Math.pow(reflectionDirection.getZeroOrCosAngel(intersection.directionToRayStart), intersection.surface.material.phong_coefficient);
        return intersection.surface.material.specular_color.multiply(light.color).multiply(intensity);
    }

    private Color diffuseColor(Light light, Intersection intersection){
        MyVector directionToLight = new MyVector(intersection.position, light.position);
        double intensity = intersection.surface.getNormalToDirection(intersection.position, intersection.directionToRayStart).getCosAngel(directionToLight);
        return light.color.multiply(intersection.surface.material.defuse_color).multiply(intensity);
    }
    //</editor-fold>


}
