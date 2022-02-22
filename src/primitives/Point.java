package primitives;


public class Point {
    protected Double3 xyz;


    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    @Override
    public String toString() {
        return xyz.toString();
    }

    public Point add(Vector vector) {
        return new Point(this.xyz.d1 + vector.xyz.d1,this.xyz.d2 + vector.xyz.d2,this.xyz.d3 + vector.xyz.d3);
    }


    public Vector subtract(Point p1) {
        return new Vector(p1.xyz.d1 - this.xyz.d1, p1.xyz.d2 - this.xyz.d2, p1.xyz.d3 - this.xyz.d3);
    }


    public double distanceSquared(Point p) {
        return (((this.xyz.d1 - p.xyz.d1) + (this.xyz.d2 - p.xyz.d2) + (this.xyz.d3 - p.xyz.d3)) * ((this.xyz.d1 - p.xyz.d1) + (this.xyz.d2 - p.xyz.d2) + (this.xyz.d3 - p.xyz.d3)));
    }

    public double distance(Point p1,Point p2)
    {
        return Math.sqrt(p1.distanceSquared(p2));
    }




}