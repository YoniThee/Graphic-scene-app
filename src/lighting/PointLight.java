package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{
    private Point position;
    private Double kC, kA, kQ;
    protected PointLight(Color color) {
        super(color);
        kC = 0.0;
        kA = 0.0;
        kQ = 0.0;
    }

    public PointLight setkC(Double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setkA(Double kA) {
        this.kA = kA;
        return this;
    }

    public PointLight setkQ(Double kQ) {
        this.kQ = kQ;
        return this;
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
