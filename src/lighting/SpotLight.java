package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

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

    @Override
    public Color getIntensity(Point p) {
        double max = Math.max(0, direction.dotProduct(getL(p)));
        return super.getIntensity(p).scale(max);
    }

    public SpotLight setNarrowBeam(int narrowBeam) {
        return this;
    }
}
