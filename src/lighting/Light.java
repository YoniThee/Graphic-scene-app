package lighting;

import primitives.Color;

/**
 * class light have responsibility for all the light
 *
 * @Auther Shay Dopelt && Yehonatan Thee
 * */
public abstract class Light {
    private Color intensity ;

    protected Light(Color color) {
        intensity = color;
    }

    public Color getIntensity() {
        return intensity;
    }
}
