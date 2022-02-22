package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube implements Geometry {

    protected  double radius;
    protected Ray axisRay;

    public Tube(double radius, Ray axisRay) {
        this.radius = radius;
        this.axisRay = axisRay;
    }


    @Override
    public Vector getNormal(Point point) {
        return null;
    }

}
