package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

import static primitives.Util.*;

/**
 * Camera class is create the view plane and all the rays from camera to the objects at the destination.
 * the class also can crate pictures by using imageWriter class
 *
 * @author Shay Dopelt && Yehonatan Thee
 */
public class Camera {
    private Point p0;
    private Vector vRight;
    private Vector vUp;
    private Vector vTo;
    private double height;
    private double width;
    private double distance;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracerBase;


    private boolean AntiAlaising = false;
    private boolean SuperAduptive = false;
    private int DepthOfRec = 16;


    public Camera(Point p0, Vector vTo, Vector vUp) {
        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        double ans = vTo.dotProduct(vUp);
        try {
            if (ans == 0) {
                this.vRight = this.vTo.crossProduct(this.vUp).normalize();
            }
            //if the tow vectors is not vertical, throw exception.
            else vRight = new Vector(0, 0, 0).normalize();
        } catch (IllegalArgumentException NotVertical) {
        }

    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getDistance() {
        return distance;
    }

    public Camera setVPSize(double width, double height){
        this.width = width;
        this.height = height;
        return this;
    }

    public Camera setVPDistance(double distance){
        this.distance = distance;
        return this;
    }

    public Camera setImageWriter(ImageWriter image){
        imageWriter = image;
        return this;
    }

    public Camera setRayTracer(RayTracerBase ray){
        rayTracerBase = ray;
        return this;
    }

    public Camera setAntiAlising(Boolean antiAlising){
        AntiAlaising = antiAlising;
        return this;

    }

    public Camera setSuperSimple(Boolean superSimple){
        SuperAduptive = superSimple;
        return this;
    }

    public Camera setDepthOfRec(int depthOfRec){
        DepthOfRec = depthOfRec;
        return this;
    }

    /**
     * This function is send ray from camera to all pixel and get the color of all the pixels at the image.
     * The func can crate high quality image by using @castBeam method or less quality image by using @castRay method
     * and both of them is using threads for faster result.
     * */
    public Camera renderImage(){
        // validation check for safety
        if(p0 == null || vRight == null || vTo == null || vUp == null || height == 0 || width == 0
        || distance == 0 || rayTracerBase == null || imageWriter == null){
            throw new MissingResourceException("one of the properties is null/empty","Camera","");
        }
        int nY = imageWriter.getNy();
        int nX = imageWriter.getNx();
        int threadsCount = 3;
        Pixel.initialize(nY, nX, 1);
        if(AntiAlaising && SuperAduptive) {
            while (threadsCount-- > 0) {
                new Thread(() -> {
                    for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone())
                        castBeam(constructFiveRays(nX, nY, pixel.col, pixel.row),
                                constructFiveRays(nX, nY, pixel.col, pixel.row), DepthOfRec, pixel);
                }).start();
            }
           Pixel.waitToFinish();
        }

        else if(AntiAlaising && !SuperAduptive) {
            int divide = 33;
            while (threadsCount-- > 0) {
                new Thread(() -> {
                    for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone())
                        castBeam(constructBeam(nX, nY, pixel.col, pixel.row,divide),
                                constructBeam(nX, nY, pixel.col, pixel.row,divide),0,pixel);
                }).start();
            }
            Pixel.waitToFinish();
        }
        else{
            while (threadsCount-- > 0) {
                new Thread(() -> {
                    for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone())
                        castRay(nX, nY, pixel.col, pixel.row);
                }).start();
            }
            Pixel.waitToFinish();
        }

        return this;
    }

    /**
     * This function is get location of specific point at the view plane and crate ray from camera
     * to the location and calculate his color, and after that write the color to the image
     * */
    private void castRay(int nY, int nX, int i, int j) {
        Ray ray = constructRay(nX, nY, i, j);
        Color color = castRay(ray);
        imageWriter.writePixel(i, j,color);
    }

    /**
     * This function is get location of specific point at the view plane and crate beam(list) of rays  from camera
     * to the location and calculate his color, and after that write the color to the image
     * */
    private void castBeam(List<RayColor> beam, List<RayColor> Totalbeam, int depth, Pixel pixel) {
        if(SuperAduptive) {

            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();
            int indexBeam = 0;
            for (var rayColor : beam) {
                //compare the color of center point at pixel to all the corners
                //stop condition for the recursive call, we got up to 4 calls
                if (depth > 0 && rayColor.color.isChange(beam.get(2).color)) {

                  depth--;
                  //reduce the pixel by 4
                  nY = nY / 2;
                  nX = nX / 2;

                  //the difference  color is #     *
                  //                            *
                  //                         *     *
                  if (indexBeam == 0) {
                      Totalbeam.add(beam.get(1));
                      Totalbeam.add(beam.get(3));
                      Totalbeam.add(beam.get(4));
                      castBeam(constructFiveRays(nX, nY, 0, 0), Totalbeam, depth, pixel);
                  }


                  //the difference  color is *     #
                  //                            *
                  //                         *     *
                  if (indexBeam == 1) {
                      Totalbeam.add(beam.get(0));
                      Totalbeam.add(beam.get(3));
                      Totalbeam.add(beam.get(4));
                      castBeam(constructFiveRays(nX, nY, 0, nX - 1), Totalbeam, depth, pixel);
                  }
                  //the difference  color is *     *
                  //                            *
                  //                         *     #
                  if (indexBeam == 3) {
                      Totalbeam.add(beam.get(0));
                      Totalbeam.add(beam.get(1));
                      Totalbeam.add(beam.get(4));
                      castBeam(constructFiveRays(nX, nY, nY - 1, nX - 1), Totalbeam, depth, pixel);
                  }
                  //the difference  color is *     *
                  //                            *
                  //                         #     *
                  if (indexBeam == 4) {
                      Totalbeam.add(beam.get(0));
                      Totalbeam.add(beam.get(1));
                      Totalbeam.add(beam.get(3));
                      castBeam(constructFiveRays(nX, nY, nY - 1, 0), Totalbeam, depth, pixel);
                  }
                }
                indexBeam++;
            }


                double rColor = 0.0, gColor = 0.0, bColor = 0.0;
                for (var ray : Totalbeam) {
                    rColor += ray.color.getColor().getRed();
                    gColor += ray.color.getColor().getGreen();
                    bColor += ray.color.getColor().getBlue();
                }

                imageWriter.writePixel(
                        pixel.row, pixel.col, new Color(
                                rColor / Totalbeam.size(),
                                gColor / Totalbeam.size(),
                                bColor / Totalbeam.size()));



        }
        else
        {
            double rColor = 0.0, gColor = 0.0,bColor =0.0;
            for (var ray : beam) {
                rColor += ray.color.getColor().getRed();
                gColor +=ray.color.getColor().getGreen();
                bColor += ray.color.getColor().getBlue();
            }
            imageWriter.writePixel(
                    pixel.row, pixel.col, new Color(
                            rColor / beam.size(),
                            gColor /  beam.size(),
                            bColor /  beam.size()));
        }
    }

    /**
     * This function is for implement Anti Aliasing super sample by 5 rays to 4 corners and to the center, another way
     * from the random option
     * */
    public List<RayColor> constructFiveRays(int  nX, int nY, int i, int j) {
    List<RayColor>myRays = new LinkedList<>();

    //pixel height
    double rY = alignZero(height / nY);

    //pixel width
    double rX = alignZero(width / nX);

    Point center = initializePC(nY,nX,i, j);

    // up left
    Ray ray1 =  new Ray(p0, center.add(vRight.scale(-rX / 2)).add(vUp.scale(rY / 2)).subtract(p0));
    myRays.add(new RayColor(ray1,rayTracerBase.traceRay(ray1)));
    // up right
    Ray ray2 = new Ray(p0, center.add(vRight.scale(rX / 2)).add(vUp.scale(rY / 2)).subtract(p0));
    myRays.add(new RayColor(ray2,rayTracerBase.traceRay(ray2)));
    // center
    Ray ray3 = constructRay(nY,nX,j, i);
    myRays.add(new RayColor(ray3,rayTracerBase.traceRay(ray3)));
    // down left
    Ray ray4 = new Ray(p0, center.add(vRight.scale(-rX / 2)).add(vUp.scale(-rY / 2)).subtract(p0));
    myRays.add(new RayColor(ray4,rayTracerBase.traceRay(ray4)));
    // down right
    Ray ray5 = new Ray(p0, center.add(vRight.scale(rX / 2)).add(vUp.scale(-rY / 2)).subtract(p0));
    myRays.add(new RayColor(ray5,rayTracerBase.traceRay(ray5)));
    //list of rays to be returned
    // *     *
    //    *
    // *     *
    return myRays;
    }
    /**
     * This function is get location at the view plane and limit of row and colum, and crate list of rays for the pixel
     * */
    public List<RayColor> constructBeam(int nX, int nY, int i , int j, double divide) {
        Point Pij = initializePC(nY,nX,i,j);
        var rayList = new LinkedList<RayColor>();
        Ray mainRay = constructRay(nX, nY, j, i);
        rayList.add(new RayColor(mainRay,rayTracerBase.traceRay(mainRay))); // The main ray

        //up left corner of pixel
        double Ry = alignZero(height/nY);
        double Rx = alignZero(width/nX);
        Point pixStart = Pij.add(vRight.scale(-Rx / 2)).add(vUp.scale(Ry / 2));

        // The formation of the rays within the division of the pixel,
        // in each square a point of intersection is selected at random
        for (double row = 0; row < divide; row++) {
            for (double col = 0; col < divide; col++) {
                Ray randomRay = randomPointRay(pixStart, col/divide, -row/divide);
                rayList.add(new RayColor(randomRay,rayTracerBase.traceRay(randomRay)));
            }
        }
        return rayList;
    }

    /**
     * Helper function for initialize the ray to the center of the pixel, this code calculate is from the lecture.
     * the function is get location of specific pixel and calculate the point
     */
    private Point initializePC(int nY,int nX,int i,int j){
        // the image's center
        Point Pc = p0.add(vTo.scale(distance));
        //height of single pixel
        double Ry = alignZero(height/nY);
        //width of single pixel
        double Rx = alignZero(width/nX);
        //amount of pixels to move in y axis from pc to i
        double Yi = alignZero(-(i - ((nY - 1) / 2d)) * Ry);
        //amount of pixels  to move in x axis from pc to j
        double Xj = alignZero((j - ((nX - 1) / 2d)) * Rx);

        Point Pij = Pc;
        if(!isZero(Xj)) {
            //only move on X axis
            Pij = Pij.add(vRight.scale(Xj));
        }
        if(!isZero(Yi)) {
            //only move on Y axis
            Pij = Pij.add(vUp.scale(Yi));
        }
        return Pij;
    }

    /**
     * The "AntiAliasing" implement is have some ways, we choose to implement by random. This function is get Point at the
     * pixel and return another point at the same pixel.
     * */
    private Ray randomPointRay(Point pixStart, double col, double row) {
        Point point = pixStart;
        if(!isZero(col)) {
            //only move on X axis
            point = point.add(vRight.scale(random(0, col)));
        }
        if(!isZero(row)) {
            //only move on Y axis
            point = point.add(vUp.scale(random(row, 0)));
        }
        return new Ray(p0, point.subtract(p0));
    }

    /**
     * This function is connect the Camera to The RayTracer for get all the functionality of there
     * */
    private Color castRay(Ray ray) {
        return rayTracerBase.traceRay(ray);
    }

    /**
     * This function is crate a grid on picture ay interval
     * */
    public void printGrid(int interval, Color color){
        if(imageWriter == null)
            throw new MissingResourceException("this image not initialized yet","Camera","");
        for (int i = 0; i <imageWriter.getNx() ; i+=interval) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                imageWriter.writePixel(i, j, color);
            }
        }
        for (int i = 0; i <imageWriter.getNx() ; i++) {
            for (int j = 0; j < imageWriter.getNy(); j+=interval) {
                imageWriter.writePixel(i, j, color);
            }
        }
    }

    /**
     * This function is crate the file of the image
     * */
    public void writeToImage(){
        if(imageWriter == null)
            throw new MissingResourceException("This image is missing parameter","Camera","");
        else
            imageWriter.writeToImage();
    }

    /**
     * This function is crate the correct ray for all pixel, by calculate distance of view plane from camera
     * */
    public Ray constructRay(int Nx, int Ny, int j, int i){
        //we start calculate from the center of the pixel (PC = Point center)
        Point intersectionPoint = initializePC(Ny,Nx,i,j);
        Vector dir = intersectionPoint.subtract(p0);
        if(!isZero(dir.length())) {
            Ray r = new Ray(p0, dir);
            return r;
        }
        else{
            throw new IllegalArgumentException();
        }
    }
    /**
     * PDS for better use at anti aliasing
     * */
    public static class RayColor {
        //this struct got 2 properties: ray and the color that we got from the intersection of the ray with shape
        public Ray ray;
        public Color color;

        public RayColor(Ray ray, Color color) {
            this.ray = ray;
            this.color = color;
        }
    }
}
