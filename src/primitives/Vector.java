package primitives;
/*
This class is used for arithmetic operations between vectors or points
*/
public class Vector extends Point {
    public Vector(double d1,double d2, double d3) {
        super(d1,d2,d3);
        if(xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException ("exception from constructor of vector");
    }

    public Vector(Double3 xyz) {
        super(xyz);
        if(xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException ("exception from constructor of vector");

    }

    public Vector add(Vector v2)
    {
        return new Vector(this.xyz.add(v2.xyz));
    }


    public Vector subtract(Vector v2)
    {
        return new Vector(this.xyz.subtract(v2.xyz));
    }


    public Vector scale(double num)
    {
        return new Vector(xyz.d1 * num, xyz.d2 * num, xyz.d3 * num);
    }
    // Scalar multiplication func
    public double dotProduct(Vector v2)
    {
        Double3 temp = this.xyz.product(v2.xyz);
        return temp.d1 + temp.d2 + temp.d3;
    }
    public Vector crossProduct(Vector v2)
    {
        return new Vector(xyz.d2*v2.xyz.d3 - xyz.d3* v2.xyz.d2, xyz.d3* v2.xyz.d1 - xyz.d1* v2.xyz.d3,
                xyz.d1* v2.xyz.d2 - xyz.d2* v2.xyz.d1);
    }
    public double lengthSquared()
    {
        return xyz.d1*xyz.d1 + xyz.d2*xyz.d2 + xyz.d3*xyz.d3;
    }
    public double length()
    {
        return Math.sqrt(lengthSquared());
    }

    public Vector normalize()
    {
        return new Vector(xyz.reduce(length()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Double.compare(vector.xyz.d1, xyz.d1) == 0 && Double.compare(vector.xyz.d2, xyz.d2) == 0
                && Double.compare(vector.xyz.d3, xyz.d3) == 0;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + xyz.d1 +
                ", y=" + xyz.d2 +
                ", z=" + xyz.d3 +
                '}';
    }
}

