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
       // return super.getNormal(point);

        // find the vector on the lower base
        Vector v1 = point.subtract(axisRay.getP0());
        if (v1.dotProduct(axisRay.getDir()) == 0) { // the vectors is orthogonal to each other
            if (v1.length() <= radius) { // the point is on the base of the cylinder
                return axisRay.getDir();
            }
        }
        // find the vector on the upper base
        Vector v2 = axisRay.getDir().scale(axisRay.getDir().normalize().dotProduct(v1));
        if (v1.normalize().equals(v2.normalize())) {
            throw (new IllegalArgumentException("ERROR: Point is versicle to the ray"));
        } else if (v1.equals(v2)) {
            return axisRay.getDir();
        } else {
            Vector normal = v1.subtract(v2);
            return normal.length() == radius ? normal.normalize() : axisRay.getDir();
        }

    }

    @Override
    public List<Point> findIntsersections(Ray ray)
    {
        return null;
    }
}
