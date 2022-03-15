package unittest.geomatries;

import geometries.Sphere;
import geometries.Tube;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {
    /*
     * Testing Sphere
     * @author Shay Dopelt && Yheonatan Thee
     * */
    @Test
    public void testConstructor() {
    }
    /**
     * Test method for  {@link geometries.Sphere}
     **/
    @Test
    void testgetNormal() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Check the normal vector by solution for this case by calculator at internet
        Point center = new Point(1,1,1);
        Point p = new Point(2,3,4);
        Sphere sphere = new Sphere(center,2);
            assertEquals(sphere.getNormal(p), new Vector(0.2672612419124244,0.5345224838248488,0.8017837257372732),
                    "The normal vector is wrong");
        }
}