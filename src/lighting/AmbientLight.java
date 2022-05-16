package lighting;

import primitives.*;

/**
 * AmbientLight class is one type of light source that crate light to all the directions at the same
 *
 * @author Shay Dopelt && Yehonatan Thee
 */
public class AmbientLight extends Light {

    public AmbientLight(Color Ia, Double3 Ka) {
        //Ip = Ka * Ia
        super(Ia.scale(Ka));
    }

    public AmbientLight() {
        super(Color.BLACK);
    }




}
