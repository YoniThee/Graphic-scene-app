package lighting;

import primitives.*;

public class AmbientLight {

    private Color intensity;

    public AmbientLight(Color Ia, Double3 Ka) {
        //Ip = Ka * Ia
        intensity = Ia.scale(Ka);
    }

    public AmbientLight() {
        intensity = Color.BLACK;
    }

    public Color getIntensity() {
        return intensity;
    }
}
