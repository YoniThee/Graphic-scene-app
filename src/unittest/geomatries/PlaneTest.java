package unittest.geomatries;

import geometries.Plane;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import primitives.Point;

class PlaneTest {
    /*
     * Testing Plane
     * @author Shay Dopelt && Yheonatan Thee
     * */
    Point p1 = new Point(0,0,1);
    Point p2 = new Point(0,1,0);
    Point p3 = new Point(1,0,0);
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Check the mathematical operation
        Plane pl = new Plane(p1,p2,p3);

    }

        @Test
    /**Test method for {@link Plane.GetNormal}*/
    void testGetNormal() {
    }

    @Test
    /**Test method for {@link Plane.TestGetNormal}*/
    void testTestGetNormal() {
    }
}