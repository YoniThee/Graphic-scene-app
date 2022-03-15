package unittest.geomatries;

import geometries.Cylinder;
import geometries.Plane;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/*
 * Testing Cylinder
 * @author Shay Dopelt && Yehonatan Thee
 * */
class TriangleTest {
    /**
     * Test method for {@link Plane# GetNormal(Point)}
     */
    Point p1 = new Point(0,0,2);
    Point p2 = new Point(0,3,0);
    Point p3 = new Point(9,0,0);
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Check the normal vector by solution for this case by calculator at internet
        //Because plane is inheritor from plane, and now he is still implement nothing we can check by the same way we
        //checked at plane.
        Plane pl = new Plane(p1,p2,p3);
        assertEquals(pl.getNormal(new Point(1,1,1)),new Vector(-0.18181818181818182,-0.5454545454545454,
                -0.8181818181818182),"The normal is wrong");
    }
}
