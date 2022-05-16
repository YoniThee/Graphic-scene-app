package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;
/**
 * Intersectale class is responsible to all intersection of all the shapes
 * he is implemented as interface because the way to calculate intersection is difference between the shapes
 *
 * @author Shay Dopelt && Yehonatan Thee
 * */
public abstract class Intersectable{

    public List<Point> findIntsersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point). toList();

    }

    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntsersectionsHelper(ray);
    }

    /*This struct is PDS for better use at the intersectable*/
    public static class GeoPoint {
        //this struct got 2 properties: which kind of shape we are and which specific point
        public Geometry geometry;
        public Point point;

        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    protected abstract List<GeoPoint> findGeoIntsersectionsHelper(Ray ray);



}