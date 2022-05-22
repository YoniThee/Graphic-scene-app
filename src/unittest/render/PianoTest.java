package unittest.render;

import geometries.Cylinder;
import geometries.Geometry;
import geometries.Polygon;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing Project
 * 
 * @author Shay Doplet && Yehonatan Thee
 *
 */
class PianoTest {
	Color pianoColor = new Color(102,51,0);
	//crate the Resonance box
	Geometry back = new Polygon(new Point(10, 30, 10), new Point(10, 0, 10), new Point(10, 0, 0),
			new Point(10, 30, 0)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry front = new Polygon(new Point(7, 30, 10), new Point(7, 0, 10), new Point(7, 0, 0),
			new Point(7, 30, 0)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry rightSide = new Polygon(new Point(7, 0, 0), new Point(7, 0, 10), new Point(10, 0, 10),
			new Point(10, 0, 0)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry leftSide = new Polygon(new Point(7, 30, 0), new Point(7, 30, 10), new Point(10, 30, 10),
			new Point(10, 30, 0)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry roof = new Polygon(new Point(7, 30, 10), new Point(10, 30, 10), new Point(10, 0, 10),
			new Point(7, 0, 10)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	//crate keyboards
	Geometry KBfront = new Polygon(new Point(4,28,7),new Point(4,28,5),new Point(4,2,5),
			new Point(4,2,7)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry KBrightSide = new Polygon(new Point(4,2,5),new Point(4,2,7),new Point(7,2,8),
			new Point(7,2,5)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry KBleftSide = new Polygon(new Point(4,28,5),new Point(4,28,7),new Point(7,28,8)
			,new Point(7,28,5)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry KBroof = new Polygon(new Point(4,28,7),new Point(7,28,8),new Point(7,2,8),
			new Point(4,2,7)).setEmission(pianoColor).setMaterial(new Material().setKR(new Double3(0.8)))
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	//carte chair
	Geometry leg1 = new Cylinder(0.2,new Ray(new Point(3,10,0),new Vector(0,0,1)),4.0)
			.setEmission(new Color(153,102,51))
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry leg2 = new Cylinder(0.2,new Ray(new Point(0,10,0),new Vector(0,0,1)),4.0)
			.setEmission(new Color(153,102,51))
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry leg3 = new Cylinder(0.2,new Ray(new Point(3,20,0),new Vector(0,0,1)),4.0)
			.setEmission(new Color(153,102,51));
	Geometry leg4 = new Cylinder(0.2,new Ray(new Point(0,20,0),new Vector(0,0,1)),4.0)
			.setEmission(new Color(153,102,51))
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry seatFloor = new Polygon(new Point(3,10,4),new Point(0,10,4),new Point(0,20,4),
			new Point(3,20,4)).setEmission(new Color(153,102,51));
	Geometry seatRoof = new Polygon(new Point(3,10,4.5),new Point(0,10,4.5),new Point(0,20,4.5),
			new Point(3,20,4.5))
			.setEmission(new Color(153,102,51))
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));	/*Geometry seatEdge1 = new Polygon(new Point(3,10,5),new Point(0,10,5),new Point(3,10,4),
			new Point(0,10,4)).setEmission(new Color(153,102,51));
	Geometry seatEdge2 = new Polygon(new Point(3,10,5),new Point(3,20,5),new Point(3,20,4),
			new Point(0,20,4)).setEmission(new Color(153,102,51));
	Geometry seatEdge3 = new Polygon(new Point(3,10,5),new Point(0,10,5),new Point(0,20,5),
			new Point(3,20,5)).setEmission(new Color(153,102,51));
	Geometry seatEdge4 = new Polygon(new Point(3,10,5),new Point(0,10,5),new Point(0,20,5),
			new Point(3,20,5)).setEmission(new Color(153,102,51));*/


	//crate walls and floor
	Geometry wallBack = new Polygon(new Point(11,-10,0),new Point(11,-10,25),new Point(11,50,25),
			new Point(11,50,0)).setEmission(new Color(51,153,102))
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry wallSide = new Polygon(new Point(-20,50,0),new Point(-20,50,25),new Point(11,50,25),
			new Point(11,50,0)).setEmission(new Color(51,153,102))
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry floor = new Polygon(new Point(-20,50,0),new Point(-20,-10,0),new Point(11,-10,0),
			new Point(11,50,0)).setEmission(new Color(153,153,102))
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	//Two liitle lamps from the 2 sides of piano
	Geometry littleLamp1 = new Polygon(new Point(10.8,-4,15),new Point(10.8,-7,15),new Point(10.8,-7,13),
			new Point(10.8,-4,13)).setEmission(new Color(java.awt.Color.BLACK))
			.setMaterial(new Material().setKT(new Double3(0.6)));
	Geometry littleLamp2 = new Polygon(new Point(10.8,34,15),new Point(10.8,37,15),new Point(10.8,37,13),
			new Point(10.8,34,13)).setEmission(new Color(java.awt.Color.BLACK))
			.setMaterial(new Material().setKT(new Double3(0.6)));

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
	Camera camera = new Camera(new Point(-15, -15, 8), new Vector(1, 1, 0), new Vector(1, -1, 0)) //
			.setVPSize(20, 20).setVPDistance(20) //
			.setRayTracer(new RayTracerBasic(scene));


	@Test
	void CreatePiano() {
		scene.geometries.add(back,front,rightSide,leftSide,roof,KBfront,KBrightSide,KBleftSide,KBroof,wallBack,wallSide
				,floor,littleLamp1,littleLamp2,
				leg1,leg2,leg3,leg4,seatFloor, seatRoof//,seatEdge1,seatEdge2,seatEdge3,seatEdge4
				/*rightSide.setEmission(new Color(102,51,0)),leftSide.setEmission(new Color(102,51,0)),
				roof.setEmission(new Color(102,51,0))*/);
		scene.lights.add( //
				new SpotLight(new Color(400, 240, 0), new Point(-10,25,25),new Vector(1,-4,-20)) //
						.setKl(1E-5).setKq(1.5E-7));
		scene.lights.add(
				new SpotLight(new Color(400, 240, 0), new Point(10.9,-5.5,14),new Vector(-1,0,0))
						.setKl(1E-5).setKq(1.5E-7));
		scene.lights.add(
				new SpotLight(new Color(400, 240, 0), new Point(10.9,35.5,14),new Vector(-1,0,0))
						.setKl(1E-5).setKq(1.5E-7));
	/*	scene.lights.add( //
						new PointLight(new Color(400, 240, 0), new Point(-8,-5,13)) //
						.setKl(1E-5).setKq(1.5E-7));*/
		//scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.15)));

		camera.setImageWriter(new ImageWriter("Piano", 400, 400)) //
				.renderImage() //
				.writeToImage();
	}
}
