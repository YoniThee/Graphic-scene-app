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
     * This function is send ray from camera to all pixel and get the color of all the pixels at the image
     * */
    public Camera renderImage(){
        // validation check for safety
        if(p0 == null || vRight == null || vTo == null || vUp == null || height == 0 || width == 0
        || distance == 0 || rayTracerBase == null || imageWriter == null){
            throw new MissingResourceException("one of the properties is null/empty","Camera","");
        }
        // calculate color of all the pixels
        int ny = imageWriter.getNy();
        int nx = imageWriter.getNx();
        for (int i = 0; i < nx; i++) {
           for (int j = 0; j < ny; j++) {
               castRay(ny, nx, i, j);


                double rColor = 0.0, gColor = 0.0,bColor =0.0;
                double divide = 8;
                LinkedList<Ray> beam = constructBeam(imageWriter.getNx(), imageWriter.getNy(), j, i, divide);
                for (Ray ray : beam) {
                    rColor += rayTracerBase.traceRay(ray).getColor().getRed();
                    gColor += rayTracerBase.traceRay(ray).getColor().getGreen();
                    bColor += rayTracerBase.traceRay(ray).getColor().getBlue();
                }
                imageWriter.writePixel(
                        j, i, new Color(
                                rColor / (divide * divide + 1),
                                gColor / (divide * divide + 1),
                                bColor / (divide * divide + 1)));




            }

        }
        return this;
    }

    private void castRay(int ny, int nx, int i, int j) {
        Ray ray = constructRay(nx, ny, i, j);
        Color color = castRay(ray);
        imageWriter.writePixel(i, j,color);
    }

    public LinkedList<Ray> constructBeam(int nX,  int nY, int j , int i, double divide) {

        /**
         * the image's center
         */
        Point Pc = p0.add(vTo.scale(distance));

        /**
         * height of single pixel
         */
        double Ry = alignZero(height/nY);

        /**
         * width of single pixel
         */
        double Rx = alignZero(width/nX);

        /**
         * amount of pixels to move in y axis from pc to i
         */
        double Yi = alignZero(-(i - ((nY - 1) / 2d)) * Ry);

        /**
         * amount of pixels  to move in x axis from pc to j
         */
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

        var rayList = new LinkedList<Ray>();
        rayList.add(constructRay(nX, nY, j, i));
        /**
         * up left corner of pixel
         */
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

    private Color avarageColor(List<Color> antiAlisingList) {
        Color color = new Color(0.0,0.0,0.0);
        for(int i = 0; i < antiAlisingList.size();i++){
            color = color.add(antiAlisingList.get(i));
            color = color.reduce(2);
        }
        return color;
    }

    private List<Color> antiAlising(ImageWriter imageWriter, int i, int j) {
       List<Color>lst = new LinkedList<>();
        for (int x = 0; x < i; x++) {
            for (int y = 0; y < j; y++)
            {
                Ray ray = constructRay(imageWriter.getNx(),imageWriter.getNy(),x,y);
                lst.add(castRay(ray));
            }
        }
        return lst;
    }

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
        Point pCenter = p0.add(vTo.scale(distance));
        double Ry = height/Ny;
        double Rx = width/Nx;
        double Yi = -1 * (i - alignZero((double)(Ny-1)/2))*Ry;
        double Xj = (j - alignZero((double)(Nx-1)/2))*Rx;
        Point intersectionPoint = pCenter;
        if(!isZero(Xj))
        {
            intersectionPoint = intersectionPoint.add(vRight.scale(Xj));
        }
        if(!isZero(Yi))
        {
            intersectionPoint = intersectionPoint.add(vUp.scale(Yi));;
        }
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
