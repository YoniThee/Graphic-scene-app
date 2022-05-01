package unittest.geomatries;

import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.*;
import java.util.List;
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
            assertEquals(new primitives.Vector(0.2672612419124244,0.5345224838248488,0.8017837257372732),
                    sphere.getNormal(p), "The normal vector is wrong");
        }

    @Test
    /**Test method for {@link Sphere.FindIntsersections}*/
    void testFindIntsersections() {
        Sphere sphere= new Sphere(new Point(2,0,0),1d);
        Sphere sphere1 = new Sphere(new Point(0,1,2),2.5);
        // ============ Equivalence Partitions Tests ==============

        // TC02: Ray starts before and crosses the sphere-2 points
        Point p1=new Point(1.4,0.8,0);
        Point p2=new Point(2,1,0);
        List<Point> result= sphere.findGeoIntsersections(new Ray(new Point(-1,0,0),new Vector(3,1,0)));
        assertEquals(2,result.size(),"Wrong number of points");
        if (result.get(0).getX()>result.get(1).getX())
            result=List.of(result.get(1),result.get(0));
        assertEquals(result,List.of(p1,p2),"Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        Ray insideRay = new Ray(new Point(1,3,2.5),new Vector(2,1,4));
        assertEquals(List.of(new Point(1.1476032795495952,3.0738016397747976,2.7952065590991904)),
                sphere1.findGeoIntsersections(insideRay),
                "Ray from inside point is wrong calculate");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere1.findGeoIntsersections(new Ray(new Point(-5,-4,-3),new Vector(-2,-15,-6))),
                "Ray outside, never crosses the sphere is wrong ");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        result = sphere.findGeoIntsersections(new Ray(new Point(1,0,0), new Vector(1,0,0)));
        assertEquals(1, result.size(),"Wrong number of points");
        assertEquals(List.of(new Point(3,0,0)), result, "Bad intersection point");

        // TC12: Ray starts at sphere and goes outside (0 points)
        result = sphere.findGeoIntsersections(new Ray(new Point(2,1, 0), new Vector(0.5, 0, 1)));
        assertNull(result, "Ray from the sphere himself to outside is wrong");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)




        // TC16: Ray starts at the center (1 points)
        try{
            assertEquals(sphere1.findGeoIntsersections(new Ray(new Point(0,1,2),new Vector(4,3,2))),
                    new Point(0,1,2),"Ray start at the center of sphere is wrong");
        }
        catch (IllegalArgumentException rayFromCenter){}

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findGeoIntsersections(new Ray(new Point(0,1, 0), new Vector(2, 0, 0))),
                "Ray starts before the tangent point is wrong");
        // TC20: Ray starts at the tangent point
        assertNull(sphere.findGeoIntsersections(new Ray(new Point(2,1, 0), new Vector(1, 0, 0))),
                "Ray starts at the tangent point is wrong");
        // TC21: Ray starts after the tangent point
        assertNull(sphere.findGeoIntsersections(new Ray(new Point(3,1, 0), new Vector(1, 0, 0))),
                "Ray starts after the tangent point is wrong");

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line

    }
}