package geometries;

import primitives.*;

public abstract class Geometry extends Intersectable{
    public abstract Vector getNormal(Point point);
    protected Color emission = Color.BLACK;
    private Material material = new Material();


    public Material getMaterial() {
        return this.material;
    }

    public Color getEmission() {
        return this.emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;

    }
}