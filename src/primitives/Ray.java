package primitives;

import geometries.Geometries;
import geometries.Intersectable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static geometries.Intersectable.*;
import static primitives.Util.alignZero;

/*
* This class will serve some shapes
* */
public class Ray {
    private static final double DELTA = 0.001;
    final Point p0;
    final Vector dir;

    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    public Ray(Point point, Vector r, Vector n) {
        Vector delta = n.scale(DELTA);
        this.dir = r.normalize();
        if (n.dotProduct(r) < 0){
            delta = delta.scale(-1);
            this.p0 = point.add(delta);
        }
        else
           this.p0 = point;
    }

    public Point getP0() {
        return p0;
    }

    public Point getPoint(double t)
    {
        return p0.add(dir.scale(t));
    }

    public Vector getDir() {
        return dir;
    }

    public Point findClosestPoint(List<Point> intersections){
        return intersections.isEmpty() ? null
                : findClosestGeoPoint(intersections.stream().map(p -> new GeoPoint(null, p)).toList()
        ).point;
    }
    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(p0, ray.p0) && Objects.equals(dir, ray.dir);
    }

    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
        if(intersections.isEmpty())
            return null;
        GeoPoint closest = intersections.get(0);
        double tempDistance = closest.point.distanceSquared(p0);
        for (GeoPoint gp:intersections)
        {
            if(tempDistance > gp.point.distanceSquared(p0))
            {
                tempDistance = gp.point.distanceSquared(p0);
                closest = gp;
            }
        }
        return closest;
    }
}
