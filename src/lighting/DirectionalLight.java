package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{
    private Vector direction;
    protected DirectionalLight(Color color) {
        super(color);
    }

    @Override
    public Color getIntensity(Point p) {
        return null;
    }

    @Override
    public Vector getL(Point p) {
        return null;
    }
}
