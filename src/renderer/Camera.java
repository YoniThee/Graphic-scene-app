package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;
/**
 * Camera class is create the view plane and all the rays from camera to the objects at the destination
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


    public Camera(Point p0, Vector vUp, Vector vTo) {
        this.p0 = p0;
        this.vUp = vUp;
        this.vTo = vTo;
        double ans = vUp.dotProduct(vTo);
        try {
            if (ans == 0) {
                vRight = vUp.crossProduct(vTo).normalize();
                this.vUp.normalize();
                this.vTo.normalize();
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

    public Ray constructRay(int Nx, int Ny, int j, int i){
        Point pCenter = p0.add(vTo.scale(distance));
        double Ry = height/Ny;
        double Rx = width/Nx;
        double Yi = -1 * (i - alignZero((Ny-1)/2))*Ry;
        double Xj = (j - alignZero((Nx-1)/2))*Rx;
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