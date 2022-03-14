package unittest.primitives;
import geometries.Polygon;
import org.junit.jupiter.api.Test;
import primitives.*;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static primitives.Util.isZero;

/*
* Testing Vector
* @author Shay Dopelt && Yheonatan Thee
* */
class VectorTest {
    /**
     * Test method for {@link Vector}.
     */
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Vector is Zero at first ctor
        assertThrows(IllegalArgumentException.class, ()->new Vector(0, 0, 0),
                "Failed constructing a correct vector from ctor with 3 parameters");
        //TC02: Vector is Zero at second ctor
/*        try {
            Double3 p = new Double3(0,0,0);
            new Vector(p);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct vector from ctor with parameter Double3");
        }
*/
        // =============== Boundary Values Tests ==================
        // TC10: The parameters at the first ctor is close to zero
            assertThrows(IllegalArgumentException.class, //
                () -> new Vector(0.000000000000000000000000001,0.000000000000000000000000001,
                        0.000000000000000000000000001),
                "Failed constructing a correct vector from ctor with 3 parameters");

        // TC11: The parameters at the second ctor is close to zero
/*            Double3 p = new Double3(0.000000000001,0.000000000001,0.000000000001);
            assertThrows(IllegalArgumentException.class, //
                () -> new Vector(p),
                "Constructed a polygon with vertix on a side");
*/
    }
    /**
     * Test method for {@link primitives.Vector#add(Vector)}.
     */
    @org.junit.jupiter.api.Test
    void add() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Check the mathematical operation
        assertEquals(new Vector(3,5,7),v1.add(new Vector(2,3,4)),"The add function is wrong");

    }
    /**
     * Test method for {@link primitives.Vector#subtract(Point)}.
     */
    @org.junit.jupiter.api.Test
    void subtract() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Check the mathematical operation
        assertEquals(new Vector(-1,-2,-3),v1.subtract(new Point(2,4,6)),"The subtruct function is wrong");
    }
    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @org.junit.jupiter.api.Test
    void scale() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Check the mathematical operation
        assertEquals(new Vector(3,6,9),v1.scale(3),"The subtruct function is wrong");
    }
    /**
     * Test method for {@link Vector#dotProduct(Vector)} .
     */
    @org.junit.jupiter.api.Test
    void dotProduct() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Check dot product for orthogonal vectors
        assertTrue(isZero(v1.dotProduct(v3)),"dotProduct() for orthogonal vectors is not zero");
        //TC02: Check the mathematical operation
        assertTrue (isZero(v1.dotProduct(v2) + 28),"dotProduct() wrong value");
    }
    /**
     * Test method for {@link Vector#crossProduct(Vector)} .
     */
    @org.junit.jupiter.api.Test
    void crossProduct() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Check the mathematical operation
        Vector vr = v1.crossProduct(v3);
        assertTrue(isZero(vr.length() - v1.length() * v3.length()),
                "crossProduct() wrong result length\"");
        //TC02: Check if the vector is not orthogonal in two ways.
        assertTrue (isZero(vr.dotProduct(v1)) || isZero(vr.dotProduct(v3)),
                "crossProduct() result is not orthogonal to its operands");

        // =============== Boundary Values Tests ==================
        //TC10:test zero vector
        assertThrows(IllegalArgumentException.class,()->v1.crossProduct(v2),"crossProduct()" +
                " for parallel vectors does not throw an exception");

    }
    /**
    * Test method for {@link Vector#lengthSquared()} .
    */
    @org.junit.jupiter.api.Test
    void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: check the mathematical operation
        Vector v1 = new Vector(1, 2, 3);
        assertTrue(isZero(v1.lengthSquared() - 14), "lengthSquared() wrong value");
    }
    /**
     * Test method for {@link Vector#length()} .
     */
    @org.junit.jupiter.api.Test
    void length() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: check the mathematical operation
        assertTrue (isZero(new Vector(0, 3, 4).length() - 5),"length() wrong value");
    }
    /**
     * Test method for {@link Vector#normalize()} .
     */
    @org.junit.jupiter.api.Test
    void normalize() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Check the mathematical operation
        Vector u = v1.normalize();
        assertTrue(isZero(u.length() - 1),"the normalized vector is not a unit vector");

        //TC02: Test that the vectors are co-lined
        assertThrows(IllegalArgumentException.class,()-> v1.crossProduct(u),
                "the normalized vector is not parallel to the original one");

        //TC03: Another mathematical operation
        assertTrue(!(v1.dotProduct(u) < 0),"the normalized vector is opposite to the original one");

    }
}