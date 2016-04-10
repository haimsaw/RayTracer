package RayTracing;

import java.awt.*;
import java.awt.color.*;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 *  Main class for ray tracing exercise.
 */
public class RayTracer {

    public int imageWidth;
    public int imageHeight;
    public List<Surface> surfaces;
    public List<Light> lights;
    public Camera camera;
    public Color backgroundColor;
    public int ShadowRaysAmount;
    public int maxRecursion;
    public double screenToImageRatio;

    /**
     * Runs the ray tracer. Takes scene file, output image file and image size as input.
     */
    public static void main(String[] args) {

        try {

            RayTracer tracer = new RayTracer();

            // Default values:
            tracer.imageWidth = 500;
            tracer.imageHeight = 500;

            if (args.length < 2)
                throw new RayTracerException("Not enough arguments provided. Please specify an input scene file and an output image file for rendering.");

            String sceneFileName = args[0];
            String outputFileName = args[1];

            if (args.length > 3)
            {
                tracer.imageWidth = Integer.parseInt(args[2]);
                tracer.imageHeight = Integer.parseInt(args[3]);
            }


            // Parse scene file:
            tracer.parseScene(sceneFileName);

            // Render scene:
            tracer.renderScene(outputFileName);

//		} catch (IOException e) {
//			System.out.println(e.getMessage());
        } catch (RayTracerException e) {
            // e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }


    }

    public RayTracer() {
        this.surfaces = new ArrayList<Surface>();
        this.lights = new ArrayList<Light>();
    }

    /**
     * Parses the scene file and creates the scene. Change this function so it generates the required objects.
     */
    public void parseScene(String sceneFileName) throws IOException, RayTracerException
    {
        FileReader fr = new FileReader(sceneFileName);

        BufferedReader r = new BufferedReader(fr);
        String line = null;
        int lineNum = 0;
        System.out.println("Started parsing scene file " + sceneFileName);

        List<Material> materialsList = new ArrayList<Material>();

        while ((line = r.readLine()) != null)
        {
            line = line.trim();
            ++lineNum;

            if (line.isEmpty() || (line.charAt(0) == '#'))
            {  // This line in the scene file is a comment
                continue;
            }
            else
            {
                String code = line.substring(0, 3).toLowerCase();
                // Split according to white space characters:
                String[] params = line.substring(3).trim().toLowerCase().split("\\s+");

                if (code.equals("cam"))
                {
                    camera = Factory.createCamera(params, imageHeight, imageWidth); // TODO make sure image width = screen width
                    System.out.println(String.format("Parsed camera parameters (line %d)", lineNum));
                }
                else if (code.equals("set"))
                {
                    double red, green, blue;
                    red = Float.parseFloat(params[0]);
                    green = Float.parseFloat(params[1]);
                    blue = Float.parseFloat(params[2]);
                    backgroundColor = new Color(red, green, blue);
                    ShadowRaysAmount = Integer.parseInt(params[3]);
                    maxRecursion = Integer.parseInt(params[4]);
                    System.out.println(String.format("Parsed general settings (line %d)", lineNum));
                }
                else if (code.equals("mtl"))
                {
                    materialsList.add(Factory.createMaterial(params));
                    System.out.println(String.format("Parsed material (line %d)", lineNum));
                }
                else if (code.equals("sph"))
                {
                    Sphere sphere = Factory.createSphere(params, materialsList);
                    surfaces.add(sphere);
                    // Example (you can implement this in many different ways!):
                    // Sphere sphere = new Sphere();
                    // sphere.setCenter(params[0], params[1], params[2]);
                    // sphere.setRadius(params[3]);
                    // sphere.setMaterial(params[4]);
                    System.out.println(String.format("Parsed sphere (line %d)", lineNum));
                }
                else if (code.equals("pln"))
                {
                    Plane plane = Factory.createPlane(params, materialsList);
                    surfaces.add(plane);
                    System.out.println(String.format("Parsed plane (line %d)", lineNum));
                }
                else if (code.equals("cyl"))
                {
                    Cylinder cylinder = Factory.createCylinder(params, materialsList);
                    surfaces.add(cylinder);
                    System.out.println(String.format("Parsed cylinder (line %d)", lineNum));
                }
                else if (code.equals("lgt"))
                {
                    Light light = Factory.createLight(params);
                    lights.add(light);
                    System.out.println(String.format("Parsed light (line %d)", lineNum));
                }
                else
                {
                    System.out.println(String.format("ERROR: Did not recognize object: %s (line %d)", code, lineNum));
                }
            }
        }

        this.screenToImageRatio = camera.screen_height/this.imageHeight;

        // It is recommended that you check here that the scene is valid,
        // for example camera settings and all necessary materials were defined.

        System.out.println("Finished parsing scene file " + sceneFileName);

    }

    /**
     * Renders the loaded scene and saves it to the specified file location.
     */
    public void renderScene(String outputFileName) {
        long startTime = System.currentTimeMillis();

        // Create a byte array to hold the pixel data:
        byte[] rgbData = new byte[this.imageWidth * this.imageHeight * 3];

        int height, width;
        for (height = 0; height < imageHeight; height++) {
            for (width = 0; width < imageWidth; width++) {
                Color color;
                Ray ray = camera.getRay(height, width, screenToImageRatio);
                Intersection intersection =  ray.getClosestIntersection(this.surfaces);
                color = intersection != null ? new ColorCalculator(intersection, ShadowRaysAmount, surfaces).getColor(lights) : backgroundColor;
                this.setData(height, width, color, rgbData);


            }
        }




        /**
        // Put your ray tracing code here!
        //
        // Write pixel Color values in Color format to rgbData:
        // Pixel [x, y] red component is in rgbData[(y * this.imageWidth + x) * 3]
        //            green component is in rgbData[(y * this.imageWidth + x) * 3 + 1]
        //             blue component is in rgbData[(y * this.imageWidth + x) * 3 + 2]
        //
        // Each of the red, green and blue components should be a byte, i.e. 0-255

        **/
        //<editor-fold desc="end">
        long endTime = System.currentTimeMillis();
        Long renderTime = endTime - startTime;

        // The time is measured for your own conveniece, rendering speed will not affect your score
        // unless it is exceptionally slow (more than a couple of minutes)
        System.out.println("Finished rendering scene in " + renderTime.toString() + " milliseconds.");

        // This is already implemented, and should work without adding any code.
        saveImage(this.imageWidth, rgbData, outputFileName);

        System.out.println("Saved file " + outputFileName);
        //</editor-fold>

    }

    private void setData(int height, int width, Color color, byte[] rgbData) {
        int startPosition = 3*(height*this.imageWidth +width);
        rgbData [startPosition] = (byte) (color.getRed()*255);
        rgbData [startPosition + 1] = (byte) (color.getGreen()*255);
        rgbData [startPosition + 2] = (byte) (color.getBlue()*255);

    }


    //<editor-fold desc="SaveImage">
    //////////////////////// FUNCTIONS TO SAVE IMAGES IN PNG FORMAT //////////////////////////////////////////

    /*
     * Saves Color data as an image in png format to the specified location.
     */
    public static void saveImage(int width, byte[] rgbData, String fileName)
    {
        try {

            BufferedImage image = bytes2RGB(width, rgbData);
            ImageIO.write(image, "png", new File(fileName));

        } catch (IOException e) {
            System.out.println("ERROR SAVING FILE: " + e.getMessage());
        }

    }

    /*
     * Producing a BufferedImage that can be saved as png from a byte array of Color values.
     */
    public static BufferedImage bytes2RGB(int width, byte[] buffer) {
        int height = buffer.length / width / 3;
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        ColorModel cm = new ComponentColorModel(cs, false, false,
                Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        SampleModel sm = cm.createCompatibleSampleModel(width, height);
        DataBufferByte db = new DataBufferByte(buffer, width * height);
        WritableRaster raster = Raster.createWritableRaster(sm, db, null);
        BufferedImage result = new BufferedImage(cm, raster, false, null);

        return result;
    }

    public static class RayTracerException extends Exception {
        public RayTracerException(String msg) {  super(msg); }
    }
    //</editor-fold>

}
