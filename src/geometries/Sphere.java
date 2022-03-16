package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Sphere implements Geometry{
    Point center;
    double radius;

    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();}

    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }
}
