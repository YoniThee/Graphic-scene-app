package unittest.geomatries;

import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    @Test
    /**Test method for {@link Geometries.FindIntsersections}*/
    void testFindIntsersections() {
        Sphere sphere = new Sphere(new Point(1,1,0),2);
        Sphere sphere2 = new Sphere(new Point(2,1,0),2);
        Triangle triangle = new Triangle(new Point(0,0,1),new Point(0,-1,0),new Point(-1,0,0));
        Ray ray = new Ray(new Point(-0.9, 0, -1), new Vector(0.21, -0.21, 1));

        Geometries geometries = new Geometries();
        geometries.add(triangle,sphere,sphere2);
        // ============ Equivalence Partitions Tests ==============
        //TC01:Some shapes is intersected but other isn't
        assertEquals(1,geometries.findIntsersections(ray).size(),"Wrong calculate of intersection");

        // =============== Boundary Values Tests ==================
        //TC11: One collection of shapes is empty
        assertEquals(0, geometries.findIntsersections(new Ray(new Point(3,3,3),new Vector(0,0,1))).size(),
                "Wrong calculate of intersection");
        //TC12: No one shape is got intersection
        assertEquals(0,geometries.findIntsersections(new Ray(new Point(3,3,3),new Vector(0,0,1))).size(),
                "Wrong calculate of intersection");
        //TC13: Only one shape is have intersection
        assertEquals(1,geometries.findIntsersections(ray).size(),"Wrong calculate of intersection");
        //TC14: All the shapes is got intersection
        assertEquals(3,geometries.findIntsersections(new Ray(new Point(0.5,0.5,-1),new Vector(-0.5,-0.5,1))).size()
                ,"Wrong calculate of intersection");

    }
}