package unittest.primitives;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Testing Ray
 * @author Shay Dopelt && Yheonatan Thee
 * */

class RayTest {
    /**
     * Test method for {@link Ray}.
     */
    @Test
    /**Test method for {@link Ray.FindClosestPoint}*/
    void testFindClosestPoint() {
        Ray r = new Ray(new Point(0,0,0),new Vector(0,1,0));
        Point p1 = new Point(0,10,0);
        Point p2 = new Point(0,2,0);
        Point p3 = new Point(0,6,0);
        // ============ Equivalence Partitions Tests ==============
        //TC01: The closest point is in the middle of the list
        assertEquals(r.findClosestPoint(List.of(p1,p2,p3)),p2,"Wrong returned point");

        // =============== Boundary Values Tests ==================
        //TC10: No point in the list
        assertNull(r.findClosestPoint(List.of()),"This list is empty - no one point supposed to returned");

        //TC11: The first point at list is the closest
        assertEquals(r.findClosestPoint(List.of(p2,p1,p3)),p2,"Wrong returned point");

        //TC12: The last point at list is the closest
        assertEquals(r.findClosestPoint(List.of(p3,p1,p2)),p2,"Wrong returned point");

    }
}