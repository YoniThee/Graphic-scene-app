package geometries;


import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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






    @Override
    public List<Point> findIntsersections(Ray ray) {

        Vector rayDir = ray.getDir();
        double nv = rayDir.dotProduct(normal);
        if(isZero(nv))
        {
            return null;
        }

        double t = alignZero((p0.subtract(ray.getP0())).dotProduct(normal)/nv);
        if(t>0)
        {
            return List.of(ray.getP0().add(rayDir.scale(t)));
        }
        else return null;
    }
}
