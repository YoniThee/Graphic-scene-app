package renderer;

import geometries.Geometry;
import geometries.Intersectable;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

import static java.awt.Color.RED;
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
    private boolean SuperSimple = false;


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

    }  public Camera setSuperSimple(Boolean superSimple){
        SuperSimple = superSimple;
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
        if(AntiAlaising && SuperSimple) {
            int divide = 3;
            while (threadsCount-- > 0) {
                new Thread(() -> {
                    for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone())
                        //castBeam(constructBeam(nX, nY, pixel.col, pixel.row, divide), 0, pixel);
                        castBeam(constructFiveRays(nX, nY, pixel.col, pixel.row),0,pixel);
                }).start();
            }
            Pixel.waitToFinish();
        }
        if(AntiAlaising && !SuperSimple) {
            int divide = 6;
            while (threadsCount-- > 0) {
                new Thread(() -> {
                    for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone())
                        //castBeam(constructBeam(nX, nY, pixel.col, pixel.row, divide), 0, pixel);
                        castBeam(constructBeam(nX, nY, pixel.col, pixel.row,divide),0,pixel);
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
    private void castBeam(List<rayColor> beam,int depth,Pixel pixel) {
       /* Color endColor = new Color(0,0,0);
        for (var rayColor:beam) {
            endColor = endColor.add(rayColor.color);
        }
        endColor = endColor.reduce(beam.size());
        while (depth <= 8){//stop condition for the recursive call of this function
            //depth of the recursive is up to 4 times - 2*2,4*4,8*8,16*16
            if (bigChangeColor(beam,endColor)) {

                //add to the beam more rays that through the pixel for better quality
                //because we want to add more rays, but not  to lose our last calculate of random rays, we are adding
                //more rays at the same number of our depth
                beam.addAll(constructBeam(imageWriter.getNx(), imageWriter.getNy(), pixel.row, pixel.col, depth));
                depth *= 2; //continue to stop condition
                //calculate the new color, maybe now there is the average color, so we should stop the recursive.
                for (var rayColor:beam) {
                    endColor = endColor.add(rayColor.color);
                }
                endColor = endColor.reduce(beam.size());

            }
            else
                break;
        }



        imageWriter.writePixel(
                pixel.row, pixel.col,endColor);*/
        if(SuperSimple) {
            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();
            int indexBeam = 0;
            for (var rayColor : beam) {
                //compare the color of center point at pixel to all the corners
                //stop condition for the recursive call, we got up to 4 calls
                if (depth < 4 && rayColor.color.isChange(beam.get(2).color)) {
                    depth++;
                    nY = nY/(2 * depth);
                    nX = nX/(2 * depth);
                    //reduce the pixel by 4

                    //the difference  color is #     *
                    //                            *
                    //                         *     *
                    if (indexBeam == 0)
                        castBeam(constructFiveRays(nX,nY, 0, 0), depth, pixel);
                    //the difference  color is *     #
                    //                            *
                    //                         *     *
                    if (indexBeam == 1)
                        castBeam(constructFiveRays(nX, nY, 0, nX-1), depth, pixel);
                    //the difference  color is *     *
                    //                            *
                    //                         *     #
                    if (indexBeam == 3)
                        castBeam(constructFiveRays(nX, nY, nY-1, nX-1), depth, pixel);
                    //the difference  color is *     *
                    //                            *
                    //                         #     *
                    if (indexBeam == 4)
                        castBeam(constructFiveRays(nX, nY, nY-1, 0), depth, pixel);
                }
                indexBeam++;
            }
            Color finalColor = new Color(0,0,0);
            for (var rayColor:beam) {
                finalColor = finalColor.add(rayColor.color);
            }
            imageWriter.writePixel(pixel.row,pixel.col,finalColor.reduce(beam.size()));
        }
        else
        {
            Color finalColor = new Color(0,0,0);
            for (var ray : beam) {
                finalColor = finalColor.add(ray.color);
            }
            imageWriter.writePixel(pixel.row, pixel.col, finalColor.reduce(beam.size()));
        }

    }

public List<rayColor> constructFiveRays(int nX, int nY,int i, int j) {
    List<rayColor>myRays = new LinkedList<>();
    /**
     * pixel height
     */
    double rY = alignZero(height / nY);
    /**
     * pixel width
     */
    double rX = alignZero(width / nX);

    Point center = initializePC(nY,nX,i,j);

    // up left
    Ray ray1 =  new Ray(p0, center.add(vRight.scale(-rX / 2)).add(vUp.scale(rY / 2)).subtract(p0));
    myRays.add(new rayColor(ray1,rayTracerBase.traceRay(ray1)));
    // up right
    Ray ray2 = new Ray(p0, center.add(vRight.scale(rX / 2)).add(vUp.scale(rY / 2)).subtract(p0));
    myRays.add(new rayColor(ray2,rayTracerBase.traceRay(ray2)));
    // center
    Ray ray3 = constructRay(nY,nX,j,i);
    myRays.add(new rayColor(ray3,rayTracerBase.traceRay(ray3)));
    // down left
    Ray ray4 = new Ray(p0, center.add(vRight.scale(-rX / 2)).add(vUp.scale(-rY / 2)).subtract(p0));
    myRays.add(new rayColor(ray4,rayTracerBase.traceRay(ray4)));
    // down right
    Ray ray5 = new Ray(p0, center.add(vRight.scale(rX / 2)).add(vUp.scale(-rY / 2)).subtract(p0));
    myRays.add(new rayColor(ray5,rayTracerBase.traceRay(ray5)));
    //list of rays to be returned
    // *     *
    //    *
    // *     *
    return myRays;
}

    private boolean bigChangeColor(List<rayColor> beam,Color avaregeColor) {
        for (rayColor rayColor:beam) {
            if(avaregeColor.isChange(rayTracerBase.traceRay(rayColor.ray)))
                return true;
        }
        return false;
    }

    /**
     * This function is get location at the view plane and limit of row and colum, and crate list of rays for the pixel
     * */
    public List<rayColor> constructBeam(int nX,  int nY, int i , int j, double divide) {
        Point Pij = initializePC(nY,nX,i,j);
        var rayList = new LinkedList<rayColor>();
        Ray mainRay = constructRay(nX, nY, j, i);
        rayList.add(new rayColor(mainRay,rayTracerBase.traceRay(mainRay))); // The main ray

        /**
         * up left corner of pixel
         */
        double Ry = alignZero(height/nY);
        double Rx = alignZero(width/nX);
        Point pixStart = Pij.add(vRight.scale(-Rx / 2)).add(vUp.scale(Ry / 2));
        // The formation of the rays within the division of the pixel,
        // in each square a point of intersection is selected at random
        for (double row = 0; row < divide; row++) {
            for (double col = 0; col < divide; col++) {
                Ray randomRay = randomPointRay(pixStart, col/divide, -row/divide);
                rayList.add(new rayColor(randomRay,rayTracerBase.traceRay(randomRay)));
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
    public static class rayColor {
        //this struct got 2 properties: ray and the color that we got from the intersection of the ray with shape
        public Ray ray;
        public Color color;

        public rayColor(Ray ray, Color color) {
            this.ray = ray;
            this.color = color;
        }
    }
}
