package geometries;


import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry
{
    Point p0;
    Vector normal;

    public Plane(Point p0, Vector normal) {
        this.p0 = p0;
        this.normal = normal;
    }

    public Plane(Point p1, Point p2, Point p3) {

    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
