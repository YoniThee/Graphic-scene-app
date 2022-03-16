package unittest.geomatries;

import geometries.Tube;
import org.junit.jupiter.api.Test;
import primitives.*;


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
        ///////////////////its still not true
     //   Ray r = new Ray(new Point(1,2,3),new Vector(1,1,1));
      //  Tube t = new Tube(3,r);
      //  assertThrows(IllegalArgumentException.class, ()-> t.getNormal(new Point(1,2,3)),
      //          " Point is versicle to the ray");
        //assertEquals(t.getNormal(new Point(1,2,3)),new Vector(5,5,5), "The normal of Tube is wrong");


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
}