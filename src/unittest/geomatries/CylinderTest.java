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
        List<Point> result = cylinder.findIntsersections(new Ray(new Point(0,-4,2),new Vector(0,8,0)));
        assertEquals(2,result.size(),"wrong number of points");
        assertEquals(List.of(new Point(0.0,3.968564716806984,2.0),new Point(0.0,-1.3018980501403163,2.0)),result
                ,"wrong intersect points");

        //TC02: Ray from outside under the base intersect also sides(2 points)
        result = cylinder.findIntsersections(new Ray(new Point(0,-4,0),new Vector(0,8,2)));
        assertEquals(2,result.size(),"wrong number of points");
        assertEquals(List.of(new Point(0.0,3.9657069637894367,1.9914267409473592),new Point(0.0,0.5714285714285712,1.1428571428571428)),result,
                "wrong intersect points");

        //TC03: Ray from outside under the base intersect just the base(2 point)
        result = cylinder.findIntsersections(new Ray(new Point(0,-1,0),new Vector(0,1,3)));
        assertEquals(2,result.size(),"wrong number of points");
        assertEquals(List.of(new Point(0.0,-0.5,1.5),new Point(0.0,0.448683298050514,4.346049894151542)),result,"wrong intersect point\\s");

        assertEquals(List.of(),cylinder.findIntsersections(new Ray(new Point(0,4,1),new Vector(0,1,3)))
                ,"wrong intersect point");

        // =============== Boundary Values Tests ==================
        //**** Group: ray from inside
        //TC10: Ray from inside intersect one base(1 point)
        assertEquals(List.of(new Point(0,0.25,1.25)),cylinder.findIntsersections(new Ray(new Point(0,1,2),new Vector(0,-1,-1))),
                "wrong intersect point from inside to base");

        //TC11: Ray from inside intersect the side(1 point)
        assertEquals( List.of(new Point(-2.479919353527449,1.0,2.0)),cylinder.findIntsersections(new Ray(new Point(0,1,2),new Vector(-2.5,0,0))),
               "wrong intersect point from inside to side");

        //**** Group: ray on tangent
        //TC12: Ray on tangent(0 point)
        assertEquals(List.of(),cylinder.findIntsersections(new Ray(new Point(2.5,1,1),new Vector(0,1,3))),
                "wrong intersect point");
        //TC13: Ray on ths base(0 point)
        assertEquals(List.of(),cylinder.findIntsersections(new Ray(new Point(2.5,1,1),new Vector(2,0,0))),
                "wrong intersect point");
    }
}
