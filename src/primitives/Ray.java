package primitives;

import java.util.List;
import java.util.Objects;
/*
* This class will serve some shapes
* */
public class Ray {
    final Point p0;
    final Vector dir;

    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
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

    public Point findClosestPoint(List<Point> lst){
       if(lst.isEmpty())
           return null;
       Point closest = lst.get(0);
       double tempDistance = closest.distanceSquared(p0);
       for (Point p:lst)
       {
            if(tempDistance > p.distanceSquared(p0))
            {
                tempDistance = p.distanceSquared(p0);
                closest = p;
            }
       }
       return closest;
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

}
