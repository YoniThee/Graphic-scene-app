package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Tube class is type of shape, in this class there are calculating of intersection for this shape
 *
 * @author Shay Dopelt && Yehonatan Thee
 */
public class Tube extends Geometry {

    protected  double radius;
    protected Ray axisRay;

    public Tube(double radius, Ray axisRay) {
        this.radius = radius;
        this.axisRay = axisRay;
    }


    @Override
    public Vector getNormal(Point point) {
        double t = axisRay.getDir().dotProduct((point.subtract(axisRay.getP0())));
        if (t == 0) {
            throw new IllegalArgumentException("ERROR: Point is versicle to the ray");
        }
        Point o = axisRay.getP0().add(axisRay.getDir().scale(t));
        return point.subtract(o).normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntsersectionsHelper(Ray ray) {
   /*
        The equation for a tube of radius r oriented along a line pa + vat:
        (q - pa - (va,q - pa)va)2 - r2 = 0
        get intersections using formula : (p - pa + vt - (va,p - pa + vt)va)^2 - r^2 = 0
        reduces to at^2 + bt + c = 0
        with a = (v - (v,va)va)^2
             b = 2 * (v - (v,va)va,∆p - (∆p,va)va)
             c = (∆p - (∆p,va)va)^2 - r^2
        where  ∆p = p - pa
        */
        Vector v = ray.getDir();
        Vector va = axisRay.getDir();
        //vectors are parallel and there is no intersections.
        if (v.normalize().equals(va.normalize()))
            return null;
        //use of calculated variables to avoid vector ZERO
        double vva;
        double pva;
        double a;
        double b;
        double c;
        //check every variables to avoid ZERO vector
        if (ray.getP0().equals(axisRay.getP0())) {
            vva = v.dotProduct(va);
            if (vva == 0) {
                a = v.dotProduct(v);
            } else {
                a = (v.subtract(va.scale(vva))).dotProduct(v.subtract(va.scale(vva)));
            }
            b = 0;
            c = -radius * radius;
        } else {
            Vector deltaP = ray.getP0().subtract(axisRay.getP0());
            vva = v.dotProduct(va);
            pva = deltaP.dotProduct(va);
            if (vva == 0 && pva == 0) {
                a = v.dotProduct(v);
                b = 2 * v.dotProduct(deltaP);
                c = deltaP.dotProduct(deltaP) - radius * radius;
            } else if (vva == 0) {
                a = v.dotProduct(v);
                if (deltaP.equals(va.scale(deltaP.dotProduct(va)))) {
                    b = 0;
                    c = -radius * radius;
                } else {
                    b = 2 * v.dotProduct(deltaP.subtract(va.scale(deltaP.dotProduct(va))));
                    c = (deltaP.subtract(va.scale(deltaP.dotProduct(va))).dotProduct(deltaP.subtract(va.scale(deltaP.dotProduct(va))))) - radius * radius;
                }
            } else if (pva == 0) {
                a = (v.subtract(va.scale(vva))).dotProduct(v.subtract(va.scale(vva)));
                b = 2 * v.subtract(va.scale(vva)).dotProduct(deltaP);
                c = (deltaP.dotProduct(deltaP)) - radius * radius;
            } else {
                a = (v.subtract(va.scale(vva))).dotProduct(v.subtract(va.scale(vva)));
                if (deltaP.equals(va.scale(deltaP.dotProduct(va)))) {
                    b = 0;
                    c = -radius * radius;
                } else {
                    b = 2 * v.subtract(va.scale(vva)).dotProduct(deltaP.subtract(va.scale(deltaP.dotProduct(va))));
                    c = (deltaP.subtract(va.scale(deltaP.dotProduct(va))).dotProduct(deltaP.subtract(va.scale(deltaP.dotProduct(va))))) - radius * radius;
                }
            }
        }
        //calculate delta for result of equation
        double delta = b * b - 4 * a * c;
        // no intersections
        if (delta <= 0) {
            return null;
        } else {
            //calculate points taking only those with t > 0
            double t1 = alignZero((-b - Math.sqrt(delta)) / (2 * a));
            double t2 = alignZero((-b + Math.sqrt(delta)) / (2 * a));
            if (t1 > 0 && t2 > 0) {
                Point p1 = ray.getPoint(t1);
                Point p2 = ray.getPoint(t2);
                return List.of(new GeoPoint(this, p1), new GeoPoint(this, p2));
            } else if (t1 > 0) {
                Point p1 = ray.getPoint(t1);
                return List.of(new GeoPoint(this, p1));
            } else if (t2 > 0) {
                Point p2 = ray.getPoint(t2);
                return List.of(new GeoPoint(this, p2));
            }
        }
        return List.of();
    }
}
