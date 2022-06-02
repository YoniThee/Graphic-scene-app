package geometries;


import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Plane class represents specific two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Plane extends Geometry {
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
    protected List<GeoPoint> findGeoIntsersectionsHelper(Ray ray) {

        Vector rayDir = ray.getDir();
        double nv = rayDir.dotProduct(normal);
        if(isZero(nv))
        {
            return List.of();
        }

        double t = alignZero((p0.subtract(ray.getP0())).dotProduct(normal)/nv);

        if(t>0)
        {   try{
            if(ray.getPoint(t).subtract(p0).normalize().equals(rayDir))
                return List.of();}
            catch (Exception exception){return List.of(new GeoPoint(this,ray.getPoint(t)));}

            return List.of(new GeoPoint(this,ray.getPoint(t)));
        }
        else return  List.of();
    }

}
