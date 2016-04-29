package RayTracing;

import java.util.List;
import java.util.Random;


public class ColorCalculator {
    private int numOfShadowRays;
    public List<Surface> surfaces;
    private double epsilon;
    Color backgroundColor;
    public List<Light> lights;


    public ColorCalculator(int numOfShadowRays, List<Surface> surfaces, Color backgroundColor, List<Light> lights) {
        this.surfaces = surfaces;
        this.numOfShadowRays = numOfShadowRays ;
        epsilon = 0.00001;
        this.backgroundColor =backgroundColor;
        this.lights = lights;

    }


    public Color traceRay(Ray ray, int recursionDepth) {
        Intersection intersection =  ray.getClosestIntersection(this.surfaces);
        return intersection != null ? this.getColor(lights, recursionDepth, intersection) : backgroundColor;

    }

    private Color getColor(List<Light> lights, int recursionDepth,Intersection intersection ) {
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
        //MyVector normal = intersection.surface.getNormalWithDirection(intersection.position, new MyVector(intersection.position, reflectionSrc));
        MyVector normal = intersection.surface.getNormal(intersection.position);
        return (reflectionSrc.multiply(2).projectTo(normal)).subtract(reflectionSrc).getNormalizedVector();
    }
    //</editor-fold>

    //<editor-fold desc="shadow">
    private double getShadowCoeff(Light light, Intersection intersection) {

        MyVector toIntersection = new MyVector(light.position, intersection.position).getNormalizedVector();
        double planeOffset = toIntersection.dotProduct(light.position);
        MyVector planeVector1 = getPlaneVector(toIntersection, planeOffset, light.position).multiply(light.radius);
        MyVector planeVector2 = toIntersection.crossProduct(planeVector1).getNormalizedVector().multiply(light.radius);
        MyVector rectStart = light.position.subtract(planeVector1.multiply(0.5)).subtract(planeVector1.multiply(0.5));

        Random r = new Random();
        double numOfHits = 0;
        for (int i = 0; i < numOfShadowRays; i++) {
            for (int j = 0; j < numOfShadowRays; j++) {
                double coeff1 = 0;//r.nextDouble();
                double coeff2 = 0;//r.nextDouble(); todo fix
                MyVector rayStart = rectStart.add(planeVector1.multiply((i+coeff1)/numOfShadowRays))
                        .add(planeVector2.multiply((j+coeff2)/numOfShadowRays));
                Ray ray = new Ray(rayStart, intersection.position);
                double acummelateLight = 1;
                double distanceToLight = intersection.position.distance(rayStart) - epsilon; //epsilon
                if (Math.abs(rayStart.dotProduct(toIntersection)-planeOffset) > epsilon){
                    System.out.println("ERROR");
                }
                /**
                for (Intersection shadowRayIntesection : ray.getIntersections(surfaces)){
                    if (shadowRayIntesection.position.distance(rayStart)<distanceToLight) {
                        acummelateLight *= shadowRayIntesection.surface.material.transparency;
                    }
                }
                int x = 10;
                acummelateLight = 1;
                if (intersection.surface instanceof Cylinder && ((Cylinder) intersection.surface).isPointOnHead(intersection.position)){
                    int oi
                }**/
                List<Intersection> intersections = ray.getIntersections(surfaces);
                for (Intersection shadowRayIntesection : intersections){
                    if (shadowRayIntesection.position.distance(rayStart)<distanceToLight) {
                        acummelateLight *= shadowRayIntesection.surface.material.transparency;
                        //System.out.print("H:");
                        //System.out.println(shadowRayIntesection.position);

                    }
                }

                numOfHits += acummelateLight;

            }
        }
        /**
        if (intersection.surface instanceof Cylinder && ((Cylinder) intersection.surface).isPointOnHead(intersection.position)){
            System.out.println(numOfHits);
        }**/

        return 1-light.shadow + light.shadow*numOfHits/(numOfShadowRays*numOfShadowRays);
    }

    private MyVector getPlaneVector(MyVector normal, double offset, MyVector pointOnPlane){
        double x=0,y=0,z=0;

        if (normal.get_x() != 0) {
            x = offset/normal.get_x() ;
        }
        else if(normal.get_y() != 0) {
            y = offset/normal.get_y();
        }
        else {
            z = offset/normal.get_z();
        }
        return new MyVector(x,y,z).subtract(pointOnPlane).getNormalizedVector();
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
        //return new Color(1,1,1);
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
        //double intensity = intersection.surface.getNormalWithDirection(intersection.position, directionToLight).getZeroOrCosAngel(directionToLight);
        //todo getAbsCosAngel??
        double intensity = intersection.surface.getNormal(intersection.position).getAbsCosAngel(directionToLight);
        return light.color.multiply(intersection.surface.material.defuse_color).multiply(intensity);
    }
    //</editor-fold>


}
