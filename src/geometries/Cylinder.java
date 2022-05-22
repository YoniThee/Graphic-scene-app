package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

/**
 * Cylinder class is type of Tube that have two bases
 *
 * @author Shay Dopelt && Yehonatan Thee
 */
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
    protected List<GeoPoint> findGeoIntsersectionsHelper(Ray ray)
    {

        // The procedure is as follows:
        // P1 and P2 in the cylinder, the center of the bottom and upper bases
        Point p1 = axisRay.getP0();
        Point p2 = axisRay.getPoint(height);
        Vector Va = axisRay.getDir();

        List<GeoPoint> list = super.findGeoIntsersectionsHelper(ray);

        // the intersections with the cylinder
        List<GeoPoint> result = new LinkedList<>();

        // Step 1 - checking if the intersections with the tube are points on the cylinder
        if (list != null) {
            for (GeoPoint p : list) {
                if (Va.dotProduct(p.point.subtract(p1)) > 0 && Va.dotProduct(p.point.subtract(p2)) < 0)
                    result.add(0, p);
            }
        }

        // Step 2 - checking the intersections with the bases

        // cannot be more than 2 intersections
        if(result.size() < 2) {
            //creating 2 planes for the 2 bases
            Plane bottomBase = new Plane(p1, Va);
            Plane upperBase = new Plane(p2, Va);
            GeoPoint p;

            // ======================================================
            // intersection with the bases:

            // intersections with the bottom bases
            list = bottomBase.findGeoIntersections(ray);

            if (!list.isEmpty()) {
                p = list.get(0);
                // checking if the intersection is on the cylinder base
                if (p.point.distanceSquared(p1) < radius * radius)
                    result.add(p);
            }

            // intersections with the upper bases
            list = upperBase.findGeoIntersections(ray);

            if (!list.isEmpty()) {
                p = list.get(0);
                //checking if the intersection is on the cylinder base
                if (p.point.distanceSquared(p2) < radius * radius)
                    result.add(p);
            }
        }
        // return null if there are no intersections.
        return result.size() == 0 ? List.of() : result;    }
}
