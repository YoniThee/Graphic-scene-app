package unittest.geomatries;

import geometries.Cylinder;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;

class CylinderTest {
    /*
     * Testing Cylinder
     * @author Shay Dopelt && Yheonatan Thee
     * */


    @Test
    /**Test method for {@link Cylinder.GetNormal}*/
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Cylinder cylinder = new Cylinder(2, new Ray(new Point(0, 0, 0), new Vector(0, 0, 2)), 5);
        // TC01: Normal test on the side of the cylinder
        assertEquals( new Vector(0, 1, 0),cylinder.getNormal(new Point(0, 2, 2)),"ERROR: Normal test on the tube of the cylinder");


        // TC02: Normal test on the top of the cylinder
        assertEquals( new Vector(0, 0, 1), cylinder.getNormal(new Point(1, 0, 5)),"ERROR: Normal test on the top of the cylinder");
        // TC03: Normal test on the bottom of the cylinder
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(new Point(1, 1, 0)),"ERROR: Normal test on the bottom of the cylinder");

        // ========== Boundary Values Tests ==================
        // TC11: A test on the top of the cylinder
        assertThrows(IllegalArgumentException.class, () -> {
            cylinder.getNormal(new Point(0, 0, 5));
        }, "ERROR: A test on the top of the cylinder");
        // TC12: A test on the bottom of the cylinder
        assertThrows(IllegalArgumentException.class, () -> {
            cylinder.getNormal(new Point(0, 0, 0));
        }, "ERROR: A test on the bottom of the cylinder");

    }

    @Test
    /**Test method for {@link Cylinder.FindIntsersections}*/
    void testFindIntsersections() {
        Cylinder cylinder = new Cylinder(2.5,new Ray(new Point(0,1,1),new Vector(0,1,3)),3);
        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray from outside intersect 2 sides(2 point)
        List<Point> result = cylinder.findGeoIntsersections(new Ray(new Point(0,-4,2),new Vector(0,8,0)));
        assertEquals(result.size(),2,"wrong number of points");
        assertEquals(result,List.of(new Point(0,-1.5,2),new Point(0,3.5,2))
                ,"wrong intersect points");

        //TC02: Ray from outside under the base intersect also sides(2 points)
        result = cylinder.findGeoIntsersections(new Ray(new Point(0,-4,0),new Vector(0,8,2)));
        assertEquals(result.size(),2,"wrong number of points");
        assertEquals(result,List.of(new Point(0,0,1),new Point(0,3.5,1.75)),
                "wrong intersect points");

        //TC03: Ray from outside under the base intersect just the base(2 point)
        result = cylinder.findGeoIntsersections(new Ray(new Point(0,-1,0),new Vector(0,1,3)));
        assertEquals(result.size(),2,"wrong number of points");
        assertEquals(result,List.of(new Point(0,-2/3d,1),new Point(0,0,3)),"wrong intersect point\\s");

        assertNull(cylinder.findGeoIntsersections(new Ray(new Point(0,4,1),new Vector(0,1,3)))
                ,"wrong intersect point");

        // =============== Boundary Values Tests ==================
        //**** Group: ray from inside
        //TC10: Ray from inside intersect one base(1 point)
        assertEquals(cylinder.findGeoIntsersections(new Ray(new Point(0,1,2),new Vector(0,-1,-1))),
                List.of(new Point(0,0,1)),"wrong intersect point from inside to base");

        //TC11: Ray from inside intersect the side(1 point)
        assertEquals(cylinder.findGeoIntsersections(new Ray(new Point(0,1,2),new Vector(-2.5,0,0))),
                List.of(new Point(-2.5,1,2)),"wrong intersect point from inside to side");

        //**** Group: ray on tangent
        //TC12: Ray on tangent(0 point)
        assertNull(cylinder.findGeoIntsersections(new Ray(new Point(2.5,1,1),new Vector(0,1,3))),
                "wrong intersect point");
        //TC13: Ray on ths base(0 point)
        assertNull(cylinder.findGeoIntsersections(new Ray(new Point(2.5,1,1),new Vector(2,0,0))),
                "wrong intersect point");
    }
}
