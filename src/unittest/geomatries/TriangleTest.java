package unittest.geomatries;

import geometries.Cylinder;
import geometries.Plane;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/*
 * Testing Cylinder
 * @author Shay Dopelt && Yehonatan Thee
 * */
class TriangleTest {
    /**
     * Test method for {@link Plane# GetNormal(Point)}
     */
    Point p1 = new Point(0,0,2);
    Point p2 = new Point(0,3,0);
    Point p3 = new Point(9,0,0);
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Check the normal vector by solution for this case by calculator at internet
        //Because plane is inheritor from plane, and now he is still implement nothing we can check by the same way we
        //checked at plane.
        Plane pl = new Plane(p1,p2,p3);
        assertEquals(pl.getNormal(new Point(1,1,1)),new Vector(-0.18181818181818182,-0.5454545454545454,
                -0.8181818181818182),"The normal is wrong");
    }




    /**
     * Test method for {@link geometries.Triangle#findIntsersections(primitives.Ray)}.///////////////////////////
     */
    @Test


    public void findIntersections()
    {
        Triangle triangle = new Triangle(new Point(0,2,0),new Point(2,2,0),new Point(1,2,1));



        // ============ Equivalence Partitions Tests ==============
        // TC01: A point inside the triangle
        assertEquals( List.of(new Point(1,2,0.5)),
                triangle.findIntsersections(new Ray(new Point(1,3,0.5),new Vector(0,-1,0))),
                "A point inside the triangle");

        // TC02: A point in front of one of the ribs
        assertNull(triangle.findIntsersections(new Ray(new Point(0,2,0.5),new Vector(0,1,0.25))),"A point in front of one of the ribs");

        // TC03: A point in front of the vertex
        assertNull(triangle.findIntsersections(new Ray(new Point(1,1,1),new Vector(0,1,0))),"A point in front of the vertex");



        // ========== Boundary Values Tests ==================
        // TC11: A Point on the rib
        try {
            assertNull(triangle.findIntsersections(new Ray(new Point(1.5, 2, 0.5), new Vector(0, 1, 0))),
                    "A Point on the rib");
        }
        catch (IllegalArgumentException rayFromCenter){}

        // TC12: A Point on the vertex
        try {
        assertNull(triangle.findIntsersections(new Ray(new Point(1,2,1),new Vector(0,1,0))),
               "A Point on the vertex");
        }
        catch (IllegalArgumentException TheZeroVector){}

        // TC13: A point on the continuation of one of the ribs
        try {
        assertNull(triangle.findIntsersections(new Ray(new Point(0,2,2),new Vector(-1,0,1))),"A point on the continuation of one of the ribs");
        }
        catch (IllegalArgumentException TheZeroVector){}





    }

}
