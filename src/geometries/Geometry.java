package geometries;

import primitives.*;

public abstract class Geometry extends Intersectable{
    public abstract Vector getNormal(Point point);
    protected Color emission = Color.BLACK;

    public Color getEmission() {
        return this.emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
}