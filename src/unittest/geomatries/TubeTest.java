package unittest.geomatries;

import geometries.Tube;
import org.junit.jupiter.api.Test;
import primitives.*;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class TubeTest {
    /*
     * Testing Tube
     * @author Shay Dopelt && Yheonatan Thee
     * */
    @Test
    public void testConstructor() {
    }
    @Test
    /**
     * Test method for  {@link Tube.GetNormal}
     */

    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: A simple case of a normal
        Tube tube = new Tube(2, new Ray(new Point(0, 0, -5), new Vector(0, 0, 1)));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3).length(), tube.getNormal(new Point(1, 0, 0)).length(), 0.0001,
                "ERROR: normal result is incorrect");

        // ========== Boundary Values Tests ==================
        // TC11: A Right angle with the ray source point
        assertThrows(IllegalArgumentException.class, () -> {
            Tube t = new Tube(2, new Ray(new Point(3, 4, 0), new Vector(2, -1, 0)));
            t.getNormal(new Point(2, 2, 0));
        }, "ERROR: A Right angle with the ray source point");
    }

    @Test
    /**Test method for {@link Tube.FindIntsersections}*/
    void testFindIntsersections() {
        Tube tube = new Tube(2.5,new Ray(new Point(0,1,1),new Vector(0,1,3)));
        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray from outside intersect 2 si des(2 point)
        List<Point> result = tube.findIntsersections(new Ray(new Point(0,-4,2),new Vector(0,8,0)));
        assertEquals(result.size(),2,"wrong number of points");
        assertEquals(result,List.of(new Point(0,-1.5,2),new Point(0,3.5,2))
                ,"wrong intersect points");

        //TC02: Ray from outside under the base intersect 2 sides(2 points)
        result = tube.findIntsersections(new Ray(new Point(0,-4,0),new Vector(0,8,2)));
        assertEquals(result.size(),2,"wrong number of points");
        assertEquals(result,List.of(new Point(0,0,1),new Point(0,3.5,1.75)),
                "wrong intersect points");
        //TC03: Ray from outside under the base intersect just the base(1 point)
        assertEquals(tube.findIntsersections(new Ray(new Point(0,-1,0),new Vector(0,1,3))),
                List.of(new Point(0,-2/3d,1)),"wrong intersect point");
        //TC04: No intersect points(0 point)
        assertNull(tube.findIntsersections(new Ray(new Point(0,4,1),new Vector(0,1,3)))
                ,"wrong intersect point");

        // =============== Boundary Values Tests ==================
        //**** Group: ray from inside
        //TC10: Ray from inside intersect the base(1 point)
        assertEquals(tube.findIntsersections(new Ray(new Point(0,1,2),new Vector(0,-1,-1))),
                List.of(new Point(0,0,1)),"wrong intersect point from inside to base");

        //TC11: Ray from inside intersect the side(1 point)
        assertEquals(tube.findIntsersections(new Ray(new Point(0,1,2),new Vector(-2.5,0,0))),
                List.of(new Point(-2.5,1,2)),"wrong intersect point from inside to side");

        //**** Group: ray on tangent
        //TC12: Ray on tangent(0 point)
        assertNull(tube.findIntsersections(new Ray(new Point(2.5,1,1),new Vector(0,1,3))),
                "wrong intersect point");
        //TC13: Ray on ths base(0 point)
        assertNull(tube.findIntsersections(new Ray(new Point(2.5,1,1),new Vector(2,0,0))),
                "wrong intersect point");
    }
}