package unittest.render;

import primitives.Color;
import primitives.Ray;
import renderer.RayTracerBase;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase {
    @Override
    public Color traceRay(Ray r) {
        return null;
    }

    public RayTracerBasic(Scene scene) {
        super(scene);
    }
}
