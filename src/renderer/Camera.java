package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static java.awt.Color.RED;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;
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
        double ans = vUp.dotProduct(vTo);
        try {
            if (ans == 0) {
                vRight = this.vTo.crossProduct(this.vUp);
            }
            //if the tow vectors is not vertical, throw exception.
            else vRight = new Vector(0, 0, 0);
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

    public Camera renderImage(){
        if(p0 == null || vRight == null || vTo == null || vUp == null || height == 0 || width == 0
        || distance == 0 || rayTracerBase == null || imageWriter == null){
            throw new MissingResourceException("one of the properties is null/empty","Camera","");
        }
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {

                Ray ray = constructRay(imageWriter.getNx(),imageWriter.getNy(),j,i);
                Color color = castRay(ray);
                imageWriter.writePixel(i,j,color);
            }
        }
        return this;
        //throw new UnsupportedOperationException();
    }

    private Color castRay(Ray ray) {
        return rayTracerBase.traceRay(ray);
    }

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

    public void writeToImage(){
        if(imageWriter == null)
            throw new MissingResourceException("This image is missing parameter","Camera","");
        else
            imageWriter.writeToImage();
    }

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
