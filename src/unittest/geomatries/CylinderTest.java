package unittest.geomatries;

import geometries.Cylinder;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
}