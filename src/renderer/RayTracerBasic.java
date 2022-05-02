package renderer;

import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase {
    @Override
    public Color traceRay(Ray ray) {
        var intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections.isEmpty()) return scene.background;
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint,ray);

    }

    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Color color = intersection.geometry.getEmission();
        Vector v = ray.getDir ();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material material = intersection.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color iL = lightSource.getIntensity(intersection.point);
                color = color.add(iL.scale(calcDiffusive(material, nl)),
                        iL.scale(calcSpecular(material, n, l, nl, v)));
            }
        }
        return color;
    }

    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(l.dotProduct(n)*2));
        double vrMinus = v.scale(-1).dotProduct(r);
        double vrn =Math.pow(vrMinus,material.nShininess);
        return material.kS.scale(vrn);
    }

    private Double3 calcDiffusive(Material material, double nl) {
        if (nl < 0)
        {
            nl = -nl;
        }
        return material.kD.scale(nl);
    }


    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(intersection.geometry.getEmission())
                .add(calcLocalEffects(intersection,ray));
    }


    public RayTracerBasic(Scene scene) {
        super(scene);
    }
}
