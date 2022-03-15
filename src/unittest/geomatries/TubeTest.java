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
        Ray r = new Ray(new Point(1,2,3),new Vector(1,1,1));
        Tube t = new Tube(3,r);
        assertThrows(IllegalArgumentException.class, ()-> t.getNormal(new Point(1,2,3)),
                " Point is versicle to the ray");
        assertEquals(t.getNormal(new Point(1,2,3)),new Vector(5,5,5), "The normal of Tube is wrong");
    }
}