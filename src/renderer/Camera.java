package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
        int divide = 9;
        int threadsCount = 3;
        Pixel.initialize(nY, nX, 1);
        while (threadsCount-- > 0) {
            new Thread(() -> {
                for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone())
            //        castRay(nY, nX, pixel.col, pixel.row);
                          castBeam(constructBeam(nX,nY,pixel.col, pixel.row,divide),divide,pixel);
            }).start();
        }
        Pixel.waitToFinish();
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
    private void castBeam(LinkedList<Ray> beam,int divide,Pixel pixel){
        double rColor = 0.0, gColor = 0.0,bColor =0.0;
        for (Ray ray : beam) {
            rColor += rayTracerBase.traceRay(ray).getColor().getRed();
            gColor += rayTracerBase.traceRay(ray).getColor().getGreen();
            bColor += rayTracerBase.traceRay(ray).getColor().getBlue();
        }

        imageWriter.writePixel(
                pixel.row, pixel.col, new Color(
                        rColor / (divide * divide + 1),
                        gColor / (divide * divide + 1),
                        bColor / (divide * divide + 1)));

    }

    /**
     * This function is get location at the view plane and limit of row and colum, and crate list of rays for the pixel
     * */
    public LinkedList<Ray> constructBeam(int nX,  int nY, int j , int i, double divide) {
        Point Pij = initializePC(nY,nX,j,i);
        var rayList = new LinkedList<Ray>();
        rayList.add(constructRay(nX, nY, j, i));
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
                rayList.add(randomPointRay(pixStart, col/divide, -row/divide));
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
     * The "AntiAlising" implement is have some ways, we choose to implement by random. This function is get Point at the
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
}
