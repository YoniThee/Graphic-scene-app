package scene;

import lighting.*;
import geometries.Geometries;
import primitives.*;

import java.util.LinkedList;
import java.util.List;

public class Scene {
    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = new AmbientLight();
    public Geometries geometries;
    public List<LightSource> lights = new LinkedList<>();






    public Scene(String name) {
        this.name = name;
        geometries = new Geometries();
    }

    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
