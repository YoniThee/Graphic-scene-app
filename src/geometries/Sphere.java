package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

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
    public List<Point> findIntsersections(Ray ray)
    {
        // u = Vector(O-P0)
        Vector u = center.subtract(ray.getP0());
        // Tm = V*u
        double Tm = u.dotProduct(ray.getDir());
        // d = |U|^2-Tm^2
        double d = Math.sqrt(u.lengthSquared() - (Tm*Tm));
        if(d >= this.radius)
            return null;
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
                    return List.of(p1, p2);
                } else {
                    return List.of(p1);
                }
            }
            else
                return null;
        }
    }
}
