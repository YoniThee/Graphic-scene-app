package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * SpotLight class is one type of light source that crate light one direction
 *
 * @author Shay Dopelt && Yehonatan Thee
 */
public class SpotLight extends PointLight{
    private Vector direction;
    protected SpotLight(Color color) {
        super(color);
    }

    public SpotLight(Color color,Vector direction) {

        super(color);
        this.direction = direction.normalize();
    }

    public SpotLight(Color spCL, Point spPL, Vector vector) {
        super(spCL,spPL);
        this.direction = vector.normalize();
    }

    /**
     * This function is calculate the intensity color by the formula we learned at the course
     * */
    @Override
    public Color getIntensity(Point p) {
        double max = Math.max(0, direction.dotProduct(getL(p)));
        return super.getIntensity(p).scale(max);
    }

    public SpotLight setNarrowBeam(int narrowBeam) {
        return this;
    }
}
