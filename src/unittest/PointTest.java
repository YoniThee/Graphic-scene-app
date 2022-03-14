package unittest;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void add() {
        Point p = new Point(1,2,3);
        Point Result = new Point(4,5,6);
        Vector v = new Vector(3,3,3);
        assertEquals(p.add(v),Result,"add dosent working good");

    }

    @Test
    void subtract() {
        Point p = new Point(1,2,3);
        Vector Result = new Vector(1,2,0);
        Vector v = new Vector(2,4,3);
        assertEquals(v.subtract(p),Result,"subtract dosent working good");

    }

    @Test
    void distanceSquared() {
        Point p1 = new Point(3,3,3);
        Point p2 = new Point(1,1,1);
        double Result = 12;
        assertEquals(p1.distanceSquared(p2),Result,"distanceSquared dosent working good");


    }

    @Test
    void distance() {
        Point p1 = new Point(3,3,3);
        Point p2 = new Point(1,1,1);
        double Result = 3.4641016151377544;
        assertEquals(p1.distance(p1,p2),Result,"distance dosent working good");


    }

}