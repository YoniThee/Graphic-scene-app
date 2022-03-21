package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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

    @Override
    public List<Point> findIntsersections(Ray ray) {
        return List.of();
    }
}
