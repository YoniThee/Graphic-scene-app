package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
        return List.of();
    }
}
