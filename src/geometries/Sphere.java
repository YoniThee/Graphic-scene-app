package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;


/**
 * Sphere class is type of shape.
 * The calculate of intersection for this shape is 3D in this class
 *
 * @author Shay Dopelt && Yehonatan Thee
 */
public class Sphere extends Geometry {
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
        protected List<GeoPoint> findGeoIntsersectionsHelper(Ray ray)
    {
        /*
        // u = Vector(O-P0)
        Vector u = center.subtract(ray.getP0());
        // Tm = V*u
        double Tm = u.dotProduct(ray.getDir());
        // d = |U|^2-Tm^2
        double d = Math.sqrt(u.lengthSquared() - (Tm*Tm));
        if(d >= this.radius)
            return List.of();
        else {
            // Th = sqrt(r^2-d^2)
            double Th = Math.sqrt((this.radius * this.radius) - (d * d));
            double Ti = Tm + Th;
            // only if t > 0
            if (Ti > 0) {
                //p1 = p0 + Ti*V
                Point p1 = ray.getPoint(Ti);
                Ti = alignZero(Tm - Th);
                if (Ti > 0) {
                    // p2 = p0 + Ti*V
                    Point p2 = ray.getP0().add(ray.getDir().scale(Ti));
                    return List.of(new GeoPoint(this,p1),new GeoPoint(this,p2));
                } else {
                    return List.of(new GeoPoint(this,p1));
                }
            }
            else
                return List.of();
        }
    }

         */
        if (ray.getP0() == center) {
            return List.of(new GeoPoint(this, ray.getPoint(radius)));
        }
        Point p0 = ray.getP0();
        Vector v = ray.getDir();
        Vector u = center.subtract(p0);
        double tm = v.dotProduct(u);
        double d = Math.sqrt(alignZero(u.lengthSquared() - (tm * tm)));
        if (alignZero(d - radius) > 0)
            return List.of();

        double th = Math.sqrt(alignZero((radius * radius) - (d * d)));

        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        // If the point is on the surface of the sphere - then u get 2 same points
        if (isZero(radius - d))
            t2 = -1; // remove 1 point

        if (t1 > 0 && t2 > 0) // return 2 points:
            return List.of(new GeoPoint(this, ray.getPoint(t1))
                    , new GeoPoint(this, ray.getPoint(t2)));

        if (t1 > 0) // return 1 points - if t1 > 0 but t2 < 0:
            return List.of(new GeoPoint(this, ray.getPoint(t1)));

        if (t2 > 0) // return 1 points - if t2 > 0 but t1 < 0
            return List.of(new GeoPoint(this, ray.getPoint(t2)));

        return List.of();
    }

}
