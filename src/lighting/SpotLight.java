package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight{
    private Vector direction;
    protected SpotLight(Color color) {
        super(color);
    }

    public SpotLight(Color color,  Vector direction) {
        super(color);
        this.direction = direction;
    }

    public SpotLight(Color spCL, Point spPL, Vector vector) {
        super(spCL,spPL);
        this.direction = vector;
    }

    public SpotLight setNarrowBeam(int i) {
        return this;
    }
}
