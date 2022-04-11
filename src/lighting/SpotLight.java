package lighting;

import primitives.Color;
import primitives.Vector;

public class SpotLight extends PointLight{
    private Vector direction;
    protected SpotLight(Color color) {
        super(color);
    }
}
