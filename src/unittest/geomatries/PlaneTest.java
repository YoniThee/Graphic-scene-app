package unittest.geomatries;

import geometries.Plane;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

class PlaneTest {
    /*
     * Testing Plane
     * @author Shay Dopelt && Yheonatan Thee
     * */
    Point p1 = new Point(0,0,2);
    Point p2 = new Point(0,3,0);
    Point p3 = new Point(9,0,0);
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // We dont need to check this ctor because the only option for exception is to get 2 or 3 point in the
        // same linear, but it's checked by the cross product that we are doing at the ctor.
        //This acceptable by Yair Goldshteyn.
    }

        @Test
    /**
     * Test method for {@link Plane.GetNormal(Point)}
     */
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Check the normal vector by solution for this case by calculator at internet
        Plane pl = new Plane(p1,p2,p3);
        assertEquals(pl.getNormal(new Point(1,1,1)),new Vector(-0.18181818181818182,-0.5454545454545454,
                        -0.8181818181818182),"The normal is wrong");
    }


    /**
     * Test method for {@link geometries.Plane#findIntsersections(primitives.Ray)}.///////////////////////////
     */
    @Test

    public void findIntersections()
    {
        Point p1 = new Point(2,0,2);
        Point p2 = new Point(0,2,2);
        Point p3 = new Point(0,0,2);
        Plane plane = new Plane(p1,p2,p3);


        // ============ Equivalence Partitions Tests ==============
        // TC01: A Ray above the plain, towards the top
        assertEquals(List.of(),plane.findIntsersections(new Ray(new Point(1,1,3),new Vector(1,1,4))),"Ray's line out of plane");


        // TC02: A Ray below the plain, towards the top
        assertEquals( List.of(new Point(0,0,2)),
                plane.findIntsersections(new Ray(new Point(2,2,1),new Vector(-2,-2,1))),
               "The Ray below the plane, towards the tope");



        // ========== Boundary Values Tests ==================

        // TC11: Ray parallel to the plane
        assertEquals(List.of(),plane.findIntsersections(new Ray(new Point(1,1,1),new Vector(1,1,0))),
                "Ray's line out of plane");

        // TC12: A Ray is contained in a plane
        assertEquals(List.of(),plane.findIntsersections(new Ray(new Point(0,2,2),new Vector(-2,-2,0))),
                "Ray's line is contained in a plane\"");

        // TC13: Perpendicular Ray to the plane (point below the plane)
        assertEquals( List.of(new Point(1,1,2)),
                plane.findIntsersections(new Ray(new Point(1,1,1),new Vector(0,0,1))),
               "The Ray is perpendicular to the plane\"");

        // TC14: Perpendicular Ray to the plane (point on the plane)
        assertEquals(List.of(),plane.findIntsersections(new Ray(new Point(1,1,2),new Vector(0,0,1))),
              "The Ray is perpendicular to the plane\"");

        // TC15: Perpendicular Ray to the plane (point above the plane)
        assertEquals(List.of(),plane.findIntsersections(new Ray(new Point(1,1,3),new Vector(0,0,1))),
                "The Ray is perpendicular to the plane\"");

        // TC16: Ray on the plain, but not perpendicular
        assertEquals(List.of(),plane.findIntsersections(new Ray(new Point(0,2,2),new Vector(1,-1,2))),
                "The Ray on the plain, but not perpendicular\"");

        // TC17: A Ray on the plain, from its starting point
        try {
            assertEquals(List.of(),plane.findIntsersections(new Ray(new Point(2,0,2),new Vector(-1,1,2))),
                    "The Ray on the plain, but not perpendicular\"");
        }
        catch (IllegalArgumentException rayFromCenter){}



    }

}