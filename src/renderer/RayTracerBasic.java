package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;
/**
 * RayTracerBasic class is responsible to calculate the color of all pixel at the image by reference to all the variable
 * that have any effect of the color - kind and position of the light, refraction and reflection, shade, and more
 *
 * @author Shay Dopelt && Yehonatan Thee
 */
public class RayTracerBasic extends RayTracerBase {
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * This func is get ray from the image writer, find the intersection and calculate the color of
     * all specific intersection, with reference to the light at the scene
     * */
    @Override
    public Color traceRay(Ray ray) {
        var intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections.isEmpty()) return scene.background;
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint,ray,MAX_CALC_COLOR_LEVEL,new Double3(1.0));
    }


    /**
     * This function is calculate the color of GeoPoint, and reference to distance between the location of
     * point to the light, and check if something is block the ray from light source(if there are shade)
     * in addition, the func calculate the local and global effects on the color
     * **/
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
                Double3 ktr = transparency(intersection,lightSource, l, n, nv);
                 if (ktr.higherThan(MIN_CALC_COLOR_K)) {
                     Color iL = lightSource.getIntensity(intersection.point).scale(ktr);
                     color = color.add(iL.scale(calcDiffusive(material, nl)),
                             iL.scale(calcSpecular(material,n, l, nl, v)));
               }
            }
        }
        return color;
    }

    /**
     * This function is calculate the specular component of the color
     * **/
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(l.dotProduct(n)*2));
        double vrMinus = v.scale(-1).dotProduct(r);
        double vrn =Math.pow(vrMinus,material.nShininess);
        return material.kS.scale(vrn);
    }

    /**
     * This function is calculate the diffusive component of the color
     * **/
    private Double3 calcDiffusive(Material material, double nl) {

        if (nl < 0)
        {
            nl = -nl;
        }
        return material.kD.scale(nl);
    }

    /**
     * This is the most important function in this class, she called all the other function here for get the
     * final color to return for crate the most correct image
     * **/
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color =  scene.ambientLight.getIntensity()
                .add(intersection.geometry.getEmission())
                .add(calcLocalEffects(intersection,ray));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    /**
     * This function is reference to the reflection and refraction effect on the color by the
     * formula that we learned at the theory course.  Using recursive calculate
     * **/



    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {

        Color color = Color.BLACK;
        Material material = gp.geometry.getMaterial();
        Double3 kr = material.kR;
        Double3 kkr = kr.product(k);
        // stop condition of the recursive calculate
        if (kkr.higherThan(MIN_CALC_COLOR_K)) {
            Ray reflectedRay = constructReflectedRay(ray, gp.geometry.getNormal(gp.point),gp.point);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if(reflectedPoint != null) //valid condition
                color  = color.add(calcColor(reflectedPoint, reflectedRay, level- 1, kkr).scale(kr));
        }
        Double3 kt = material.kT;
        Double3 kkt = kt.product(k);
        // stop condition of the recursive calculate
        if (kkt.higherThan(MIN_CALC_COLOR_K)) {
            Ray refrectedRay = constructRefractedRay(ray, gp.geometry.getNormal(gp.point), gp.point);
            GeoPoint refractedPoint = findClosestIntersection(refrectedRay);
           if(refractedPoint != null) //valid condition
             color = color.add(calcColor(refractedPoint, refrectedRay, level-1, kkt).scale(kt));
        }
        return color;
    }

    /**
     * This function is responsible to calculate of the reflection on the color by the next formula
     * 𝒓 = 𝒗 − 𝟐 ∙ (𝒗 ∙ 𝒏) ∙ n
     * */
    private Ray constructReflectedRay(Ray ray, Vector n, Point point) {
        Vector v = ray.getDir();
        Vector r = v.subtract(n.scale(2 * (v.dotProduct(n))));//.normalize();
        return new Ray(point, r, n);
    }

    /**
     * This function is responsible to calculate the refraction effect on the color
     * */
    private Ray constructRefractedRay(Ray ray, Vector n, Point point) {
        return new Ray(point, ray.getDir(),n);
    }

    /**
     * Helper function to get the closest GeoPoint to the ray
     * */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> geoPoints = scene.geometries.findGeoIntersections(ray);
           // return ray.findClosestGeoPoint(geoPoints);
        if (geoPoints.isEmpty()){
            return null;
        }
        Point P0 = ray.getP0();
        double smallestDistance = Double.POSITIVE_INFINITY;
        GeoPoint closestPoint = null;
        for (GeoPoint geoPoint: geoPoints) {
            double newDistance = geoPoint.point.distance(P0,geoPoint.point );
            if (newDistance < smallestDistance){
                smallestDistance = newDistance;
                closestPoint = geoPoint;
            }
        }
        return closestPoint;
    }


    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * This function is responsible to calculate the shade effect on color
     * */
    private Double3 transparency (GeoPoint gp, LightSource light, Vector l, Vector n, double nv) {

        Double3 ktr = new Double3(1.0);
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(nv < 0 ? DELTA : -1 * DELTA);
        Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections != null) {
            /*
         for (var nearPoint : intersections) {
             if(alignZero(gp.point.distance (nearPoint.point,gp.point) - light.getDistance(gp.point)) <= 0) {
                  ktr =  gp.geometry.getMaterial().kT.product(ktr);
                  if (ktr.lowerThan(MIN_CALC_COLOR_K))
                      return new Double3(0.0);
              }
         }
        }

             */
            for (GeoPoint geopoint : intersections) {
                if (alignZero(geopoint.point.distance(gp.point, geopoint.point) - light.getDistance(gp.point)) <= 0) {
                    // if (geopoint.point.distance(gp.point) <= lightDistance &&  geopoint.geometry.getMaterial().kT.equals(new Double3(0.0))){
                    // var  kt = ktr.product(geopoint.geometry.getMaterial().kT);
                    var kt = geopoint.geometry.getMaterial().kT;
                    ktr = kt.product(ktr);
                    if (ktr.lowerThan(MIN_CALC_COLOR_K))
                        return new Double3(0.0);
                }


            }
        }
        return ktr;
    }
}
