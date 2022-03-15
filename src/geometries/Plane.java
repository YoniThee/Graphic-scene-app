package geometries;


import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry
{
    private Point p0;
    private Vector normal;

    public Plane(Point p0, Vector normal) {
        this.p0 = p0;
        this.normal = normal;
    }

    public Plane(Point p1, Point p2, Point p3) {
        this.p0 = p1;
        this.normal = (p1.subtract(p2)).crossProduct(p1.subtract(p3)).normalize();
    }

    public Point getP0() {
        return p0;
    }

    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }
}
