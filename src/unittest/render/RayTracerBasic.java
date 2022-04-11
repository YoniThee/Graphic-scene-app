package unittest.render;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import renderer.RayTracerBase;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase {
    @Override
    public Color traceRay(Ray ray) {
        var intersections =  scene.geometries.findIntsersections(ray);
        if (intersections.size()==0){
            return scene.background;
        }
        Point point = ray.findClosestPoint(intersections);
        return calcColor(point);
    }

    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }


    public RayTracerBasic(Scene scene) {
        super(scene);
    }
}
