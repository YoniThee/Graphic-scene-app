package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;


/**
 * PointLight class is one type of light source that crate light to all the directions at the same
 *
 * @author Shay Dopelt && Yehonatan Thee
 */
public class PointLight extends Light implements LightSource{
    private Point position;
    private double kC, kL, kQ;
    protected PointLight(Color color) {
        super(color);/*
        kC = new Double3(1.0);
        kL = new Double3(0.0);
        kQ = new Double3(0.0);*/
        kC = 1.0;
        kL = 0.0;
        kQ = 0.0;
    }

    public PointLight(Color color, Point position) {
        super(color);
        /*kC = new Double3(1.0);
        kL = new Double3(0.0);
        kQ = new Double3(0.0);*/
        kC = 1.0;
        kL = 0.0;
        kQ = 0.0;
        this.position = position;
    }

    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * This function is calculate the intensity color by the formula we learned at the course
     * */
    @Override
    public Color getIntensity(Point p) {
        /*Color I0 = getIntensity();
        Double3 d = new Double3(p.distance(p,position));
        Double3 d2 = new Double3(p.distanceSquared(position));
        Double3 ans = kC.add( kL.product(d)).add( kQ.product(d2));
        if(ans.lowerThan(1.0))
        {
            return this.getIntensity();
        }
        return (I0.scale(1/new Vector(ans);*/
        double d2 = p.distanceSquared(position);
       double factor = kC + kL * Math.sqrt(d2) + kQ * d2;
        return getIntensity().scale(1d / factor);

    


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
