package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{
    private Point position;
    private Double kC, kA, kQ;
    protected PointLight(Color color) {
        super(color);
        kC = 1.0;
        kA = 0.0;
        kQ = 0.0;
    }

    public PointLight(Color color, Point position) {
        super(color);
        kC = 1.0;
        kA = 0.0;
        kQ = 0.0;
        this.position = position;
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
        Color I0 = getIntensity();
        double d = p.distance(p,position);
        double d2 = p.distanceSquared(position);
        double ans = (kC + kA*d + kQ*d2);
        if(ans <1)
        {
            return this.getIntensity();
        }
        return (I0.scale(1/(kC + kA*d + kQ*d2)));

    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(this.position).normalize();

    }
}
