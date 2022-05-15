package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase {
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;


    @Override
    public Color traceRay(Ray ray) {
        var intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections.isEmpty()) return scene.background;
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint,ray,MAX_CALC_COLOR_LEVEL,new Double3(MIN_CALC_COLOR_K));

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
            if (nl * nv > 0) {// sign(nl) == sing(nv)
                 if (!transparency(intersection,lightSource, l, n, nv).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(intersection.point);
                    color = color.add(iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
               }
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


    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color =  scene.ambientLight.getIntensity()
                .add(intersection.geometry.getEmission())
                .add(calcLocalEffects(intersection,ray));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));

    }

    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Material material = gp.geometry.getMaterial();
        Double3 kr = material.kR;
        Double3 kkr = kr.product(k);


        if (kkr.higherThan(MIN_CALC_COLOR_K)) {
            Ray reflectedRay = constructReflectedRay(ray, gp.geometry.getNormal(gp.point),gp.point);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            Color c = calcColor(reflectedPoint, reflectedRay, level- 1, kkr).scale(kr);
            color = color.add(c);

        }
        Double3 kt = material.kT, kkt = kt.product(k);
        if (kkt.higherThan(MIN_CALC_COLOR_K)) {
            Ray refrectedRay = constructRefractedRay(ray,gp.geometry.getNormal(gp.point),gp.point);
            GeoPoint refractedPoint = findClosestIntersection(refrectedRay);
            color = color.add(calcColor(refractedPoint, refrectedRay, level-1, kkt).scale(kt));
        }
        return color;
    }

    private Ray constructReflectedRay(Ray ray, Vector n, Point point) {
        //𝒓 = 𝒗 − 𝟐 ∙ (𝒗 ∙ 𝒏) ∙ n
        Vector v = ray.getDir();
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n))).normalize();
       // return new Ray(point, r);

        Vector delta = n.scale(DELTA);
        if (n.dotProduct(r) < 0){
            delta = delta.scale(-1);
        }
         Point p = point.add(delta);
        return new Ray(p, r);
    }

    private Ray constructRefractedRay(Ray ray, Vector n, Point point) {
      //  return new Ray(point, ray.getDir());

        Vector delta = n.scale(DELTA);
        if (n.dotProduct(ray.getDir()) < 0){
            delta = delta.scale(-1);
        }
        Point p = point.add(delta);
        Ray r = new Ray(p, ray.getDir());
        return r;


    }

    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> geoPoints = scene.geometries.findGeoIntersections(ray);
        if (geoPoints.isEmpty()) {
            return null;
        }
        else
            return ray.findClosestGeoPoint(geoPoints);
    }


    public RayTracerBasic(Scene scene) {
        super(scene);
    }
    private Double3 transparency (GeoPoint gp, LightSource light, Vector l, Vector n, double nv){
        Double3 ktr = new Double3(1.0);
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(nv < 0 ?DELTA : -1 * DELTA);
        Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (!intersections.isEmpty()) {
         for (var nearPoint : intersections) {
              if(alignZero(gp.point.distance (gp.point,point) - light.getDistance(point)) <= 0) {
                  ktr =  gp.geometry.getMaterial().kT.product(ktr);
                  if (ktr.lowerThan(MIN_CALC_COLOR_K))
                      return new Double3(0.0);
              }
         }
         //return true;
        }
        return ktr;
    }
}
