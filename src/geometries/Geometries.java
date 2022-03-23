package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;
/**
 * Geometries class is responsible for the entire unification of shapes at implement of tha
 * design pattern - "composite"
 *
 * @author Shay Dopelt && Yehonatan Thee
 */
public class Geometries implements Intersectable {
    private List<Intersectable> lst;

    public Geometries() {
        lst = new LinkedList<Intersectable>();
    }

    public Geometries(Intersectable... geometries)
    {
        lst = new LinkedList<Intersectable>();
        for (Intersectable shape: geometries)
        {
            lst.add(shape);
        }
    }

    public void add(Intersectable... geometries)
    {
        for (Intersectable shape:geometries)
        {
            lst.add(shape);
        }
    }
    @Override
    public List<Point> findIntsersections(Ray ray) {
        List<Point> temp  = new LinkedList<>();
        List<Point> ans  = new LinkedList<>();
        for (Intersectable shape:lst) {
            temp = shape.findIntsersections(ray);
            if (temp == null)
                continue;
            else if (temp.size() == 1)
                ans.add(temp.get(0));
            else {
                ans.add(temp.get(0));
                ans.add(temp.get(1));
            }
        }
        return ans ;
    }
}
