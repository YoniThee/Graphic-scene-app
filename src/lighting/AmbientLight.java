package lighting;

import primitives.*;

public class AmbientLight extends Light {

    public AmbientLight(Color Ia, Double3 Ka) {
        //Ip = Ka * Ia
        super(Ia.scale(Ka));
    }

    public AmbientLight() {
        super(Color.BLACK);
    }

}
