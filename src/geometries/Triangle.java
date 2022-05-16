package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Triangle class is type of shape. The calculate of intersection for this shape is the same as polygon
 *
 * @author Shay Dopelt && Yehonatan Thee
 */
public class Triangle extends Polygon{
    public Triangle(Point... vertices) {
        super(vertices);
    }


    @Override
    protected List<GeoPoint> findGeoIntsersectionsHelper(Ray ray) {
// A, B, C are triangle's vertices
        Point A = this.vertices.get(0);
        Point B = this.vertices.get(1);
        Point C = this.vertices.get(2);
        Plane planeContainingTriangle = new Plane(A, B, C);
        planeContainingTriangle.setEmission(this.getEmission()).setMaterial(this.getMaterial());

        Vector v1 = vertices.get(0).subtract(ray.getP0());
        Vector v2 = vertices.get(1).subtract(ray.getP0());
        Vector v3 = vertices.get(2).subtract(ray.getP0());
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();
        Vector v = ray.getDir();
        if((v.dotProduct(n1)>0 && v.dotProduct(n2)>0 && v.dotProduct(n3)>0)
                ||(v.dotProduct(n1)<0 && v.dotProduct(n2)<0 && v.dotProduct(n3)<0))
            return  planeContainingTriangle.findGeoIntsersectionsHelper(ray);

        return null;
    }




}
