package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{
    private Point position;
    private Double3 kC, kL, kQ;
    protected PointLight(Color color) {
        super(color);
        kC = new Double3(1.0);
        kL = new Double3(0.0);
        kQ = new Double3(0.0);
    }

    public PointLight(Color color, Point position) {
        super(color);
        kC = new Double3(1.0);
        kL = new Double3(0.0);
        kQ = new Double3(0.0);
        this.position = position;
    }

    public PointLight setKc(Double3 kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setKl(Double3 kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setKq(Double3 kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        Color I0 = getIntensity();
        Double3 d = new Double3(p.distance(p,position));
        Double3 d2 = new Double3(p.distanceSquared(position));
        Double3 ans = new Double3((kC.add( kL.product(d)) + kQ.product(d2)));
        if(ans <1)
        {
            return this.getIntensity();
        }
        return (I0.scale(1/(kC + kL *d + kQ*d2)));

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
