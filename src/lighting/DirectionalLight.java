package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{
    private Vector direction;
    protected DirectionalLight(Color color) {
        super(color);
    }

    public DirectionalLight(Color color, Vector direction) {
        super(color);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point p) {
        return this.getIntensity();
    }

    @Override
    public Vector getL(Point p) {

        return p.subtract(this.direction).normalize();
    }
}
