package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube implements Geometry {

    protected  double radius;
    protected Ray axisRay;
    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
