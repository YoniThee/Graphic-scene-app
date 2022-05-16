package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;


/**
 * DirectionLight class is one type of light source that crate light to all the directions at the same
 *
 * @author Shay Dopelt && Yehonatan Thee
 */
public class DirectionalLight extends Light implements LightSource{
    private Vector direction;
    protected DirectionalLight(Color color) {
        super(color);
    }

    public DirectionalLight(Color color, Vector direction) {
        super(color);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return this.getIntensity();
    }

    @Override
    public Vector getL(Point p) {

        return direction;
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}
