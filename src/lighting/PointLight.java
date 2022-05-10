package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{
    private Point position;
    private Double kC, kL, kQ;
    protected PointLight(Color color) {
        super(color);
        kC = 1.0;
        kL = 0.0;
        kQ = 0.0;
    }

    public PointLight(Color color, Point position) {
        super(color);
        kC = 1.0;
        kL = 0.0;
        kQ = 0.0;
        this.position = position;
    }

    public PointLight setKc(Double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setKl(Double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setKq(Double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        Color intensity = getIntensity();
        double dSquared = p.distanceSquared(position);
        double d = Math.sqrt(dSquared);
        double ans = (kC + kL *d + kQ*dSquared);
        if(ans <1)
        {
            return this.getIntensity();
        }
        return intensity.reduce(ans);

    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(this.position).normalize();

    }

    @Override
    public double getDistance(Point point) {

        return position.distance(point,position);
    }
}
