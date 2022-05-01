package renderer;

import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase {
    @Override
    public Color traceRay(Ray ray) {
        var intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections.isEmpty()) return scene.background;
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint);

    }

    private Color calcColor(GeoPoint intersection) {
        return scene.ambientLight.getIntensity().add(intersection.geometry.getEmission());
    }


    public RayTracerBasic(Scene scene) {
        super(scene);
    }
}
