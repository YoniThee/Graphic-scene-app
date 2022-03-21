package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Cylinder extends Tube{
    private double height;

    public Cylinder(double radius, Ray axisRay, double height1) {
        super(radius, axisRay);
        this.height = height1;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }

    @Override
    public List<Point> findIntsersections(Ray ray)
    {
        return null;
    }
}
