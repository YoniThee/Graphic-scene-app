package unittest.render;

import geometries.Plane;
import geometries.Polygon;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static java.awt.Color.BLUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing Project
 * 
 * @author Shay Toplet $$ Yehonatan Thee
 *
 */
class PianoTest {
	//crate the Resonance box
	Polygon back = new Polygon(new Point(10, 30, 10), new Point(10, 0, 10), new Point(10, 30, 0), new Point(10, 0, 0));
	Polygon front = new Polygon(new Point(7, 30, 10), new Point(7, 0, 10), new Point(7, 0, 0), new Point(7, 30, 0));
	Polygon rightSide = new Polygon(new Point(7, 0, 0), new Point(7, 0, 10), new Point(10, 0, 10), new Point(10, 0, 0));
	Polygon leftSide = new Polygon(new Point(7, 30, 0), new Point(7, 30, 10), new Point(10, 30, 10), new Point(10, 30, 0));
	Polygon roof = new Polygon(new Point(7, 30, 10), new Point(10, 30, 10), new Point(10, 0, 10), new Point(7, 0, 0));
/*	Triangle back = new Triangle(new Point(10, 30, 10), new Point(10, 0, 10), new Point(10, 30, 0));
	Triangle back2 = new Triangle(new Point(10, 30, 10), new Point(10, 0, 0), new Point(10, 30, 0));;
	Triangle front = new Triangle(new Point(5, 30, 10), new Point(5, 0, 10), new Point(5, 0, 0));
	Triangle front2 = new Triangle(new Point(5, 30, 10), new Point(5, 30, 0),  new Point(5, 0, 0));
	Triangle rightSide = new Triangle(new Point(5, 0, 0), new Point(5, 0, 10), new Point(10, 0, 10));
	Triangle rightSide2 = new Triangle(new Point(5, 0, 0), new Point(10, 0, 0), new Point(10, 0, 10));
	Triangle leftSide = new Triangle(new Point(5, 30, 0), new Point(5, 30, 10), new Point(10, 30, 10));
	Triangle leftSide2 = new Triangle(new Point(5, 30, 0), new Point(10, 30, 10), new Point(10, 30, 0));
	Triangle roof = new Triangle(new Point(5, 30, 10), new Point(10, 30, 10), new Point(10, 0, 10));
	Triangle roof2 = new Triangle(new Point(5, 30, 10),  new Point(10, 0, 10), new Point(5, 0, 0));*/
	private Scene scene = new Scene("pianoProject");
	Camera camera = new Camera(new Point(-5, -5, 8), new Vector(1, 1, 0), new Vector(-1, 1, 0)) //
			.setVPSize(20, 20).setVPDistance(5) //
			.setRayTracer(new RayTracerBasic(scene));

	@Test
	void CreatePiano() {
		scene.geometries.add(back.setEmission(new Color(102,51,0)),
				front.setEmission(new Color(102,51,0))//,
				/*rightSide.setEmission(new Color(102,51,0)),leftSide.setEmission(new Color(102,51,0)),
				roof.setEmission(new Color(102,51,0))*/);
		scene.lights.add( //
				new SpotLight(new Color(400, 240, 0), new Point(8,32,8), new Vector(1, -1, 0)) //
						.setKl(1E-5).setKq(1.5E-7));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.15)));

		camera.setImageWriter(new ImageWriter("Piano", 400, 400)) //
				.renderImage() //
				.writeToImage();
	}
}
