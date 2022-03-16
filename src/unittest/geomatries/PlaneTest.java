package unittest.geomatries;

import geometries.Plane;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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


}