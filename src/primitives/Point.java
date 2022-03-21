package primitives;


import java.util.Objects;

public class Point {
    protected Double3 xyz;


    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    public Point(Double3 xyz1) {
        xyz = xyz1;
    }

    public double getX() {
        return xyz.d1;
    }
    public double getY() {
        return xyz.d2;

    }public double getZ() {
        return xyz.d3;
    }

    @Override
    public String toString() {
        return xyz.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(xyz, point.xyz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }

    public Point add(Vector vector) {
        return new Point(this.xyz.add(vector.xyz));
        //return new Point(this.xyz.d1 + vector.xyz.d1,this.xyz.d2 + vector.xyz.d2,this.xyz.d3 + vector.xyz.d3);
    }


    public Vector subtract(Point p1) {
        return new Vector(this.xyz.subtract(p1.xyz));
        //return new Vector(this.xyz.d1 - p1.xyz.d1, this.xyz.d2 - p1.xyz.d2, this.xyz.d3 - p1.xyz.d3);
    }


    public double distanceSquared(Point p) {
        return (((this.xyz.d1 - p.xyz.d1)*(this.xyz.d1 - p.xyz.d1))+((this.xyz.d2 - p.xyz.d2)*(this.xyz.d2 - p.xyz.d2))+((this.xyz.d3 - p.xyz.d3)*(this.xyz.d3 - p.xyz.d3)));
    }

    public double distance(Point p1,Point p2)
    {
        return Math.sqrt(p1.distanceSquared(p2));
    }




}