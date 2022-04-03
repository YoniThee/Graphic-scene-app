package renderer;

import primitives.*;
import scene.Scene;

/**
 * RayTracerBase is get rays and returned colors to all the shapes
 *
 * @author Shay Dopelt && yehonatan Thee
 */
public abstract class RayTracerBase {
    protected Scene scene;

    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }
    public abstract Color traceRay(Ray r);
}
