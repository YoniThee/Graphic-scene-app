package lighting;
import primitives.Color;
import primitives.*;

/**
 * LightSource interface is the "father" of all the light types
 * @author Shay Dopelt && Yehonatan Thee
 */
public interface LightSource {
    public Color getIntensity(Point p);
    public Vector getL(Point p);

    public double getDistance(Point point);



}
