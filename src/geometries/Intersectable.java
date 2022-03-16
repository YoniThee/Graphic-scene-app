package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public interface Intersectable {

    public List<Point> findIntsersections(Ray ray);




}