package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Camera {
    private Point p;
    private Vector vRight;
    private Vector vUp;
    private Vector vTo;
    private double height;
    private double width;
    private double distance;


    public Camera(Point p, Vector vUp, Vector vTo) {
        this.p = p;
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

    public Ray constructRay(int nX, int nY, int j, int i){
        Point pc = (vTo.scale(distance));
        double Yi = -(i - ((nY-1)/2))*(height/nY);
        double Xj = (j - ((nX-1)/2))*(width/nX);
        Point intersectionPoint = pc;
        if(Xj != 0)
        {
            intersectionPoint.add(vRight.scale(Xj));

        }

        if(Yi != 0)
        {
            intersectionPoint.add(vUp.scale(Yi));;
        }
        return new Ray(p,intersectionPoint.subtract(p));

    }




}
