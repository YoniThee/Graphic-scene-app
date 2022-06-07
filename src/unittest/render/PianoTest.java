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

import static java.awt.Color.WHITE;
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
	Geometry back = new Polygon(new Point(1000, 3000, 1000), new Point(1000, 0, 1000), new Point(1000, 0, 0),
			new Point(1000, 3000, 0)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry front = new Polygon(new Point(700, 3000, 1000), new Point(700, 0, 1000), new Point(700, 0, 0),
			new Point(700, 3000, 0)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry rightSide = new Polygon(new Point(700, 0, 0), new Point(700, 0, 1000), new Point(1000, 0, 1000),
			new Point(1000, 0, 0)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry leftSide = new Polygon(new Point(700, 3000, 0), new Point(700, 3000, 1000), new Point(1000, 3000, 1000),
			new Point(1000, 3000, 0)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry roof = new Polygon(new Point(700, 3000, 1000), new Point(1000, 3000, 1000), new Point(1000, 0, 1000),
			new Point(700, 0, 1000)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	//crate keyboards
	Geometry KBfront = new Polygon(new Point(400,2800,700),new Point(400,2800,500),new Point(400,200,500),
			new Point(400,200,700)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry KBrightSide = new Polygon(new Point(400,200,500),new Point(400,200,700),new Point(700,200,800),
			new Point(700,200,500)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry KBleftSide = new Polygon(new Point(400,2800,500),new Point(400,2800,700),new Point(700,2800,800)
			,new Point(700,2800,500)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry KBroof = new Polygon(new Point(400,2800,700),new Point(700,2800,800),new Point(700,200,800),
			new Point(400,200,700)).setEmission(pianoColor).setMaterial(new Material().setKR(new Double3(0.8)))
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	//carte chair
	Geometry leg1 = new Cylinder(20,new Ray(new Point(300,1000,0),new Vector(0,0,1)),400.0)
			.setEmission(new Color(153,102,51))
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry leg2 = new Cylinder(20,new Ray(new Point(0,1000,0),new Vector(0,0,1)),400.0)
			.setEmission(new Color(153,102,51))
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry leg3 = new Cylinder(20,new Ray(new Point(300,2000,0),new Vector(0,0,1)),400.0)
			.setEmission(new Color(153,102,51));
	Geometry leg4 = new Cylinder(20,new Ray(new Point(0,2000,0),new Vector(0,0,1)),400.0)
			.setEmission(new Color(153,102,51))
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry seatFloor = new Polygon(new Point(300,1000,400),new Point(0,1000,400),new Point(0,2000,400),
			new Point(300,2000,400)).setEmission(new Color(153,102,51));
	Geometry seatRoof = new Polygon(new Point(300,1000,400.5),new Point(0,1000,400.5),new Point(0,2000,400.5),
			new Point(300,2000,400.5))
			.setEmission(new Color(153,102,51))
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	/*Geometry seatEdge1 = new Polygon(new Point(3,10,5),new Point(0,10,5),new Point(3,10,4),
			new Point(0,10,4)).setEmission(new Color(153,102,51));
	Geometry seatEdge2 = new Polygon(new Point(3,10,5),new Point(3,20,5),new Point(3,20,4),
			new Point(0,20,4)).setEmission(new Color(153,102,51));
	Geometry seatEdge3 = new Polygon(new Point(3,10,5),new Point(0,10,5),new Point(0,20,5),
			new Point(3,20,5)).setEmission(new Color(153,102,51));
	Geometry seatEdge4 = new Polygon(new Point(3,10,5),new Point(0,10,5),new Point(0,20,5),
			new Point(3,20,5)).setEmission(new Color(153,102,51));*/


	//crate walls and floor
	Geometry wallBack = new Polygon(new Point(1100,-1000,0),new Point(1100,-1000,2500),new Point(1100,5000,2500),
			new Point(1100,5000,0)).setEmission(new Color(51,153,102))
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry wallSide = new Polygon(new Point(-2000,5000,0),new Point(-2000,5000,2500),new Point(1100,5000,2500),
			new Point(1100,5000,0)).setEmission(new Color(51,153,102))
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry floor = new Polygon(new Point(-2000,5000,0),new Point(-2000,-1000,0),new Point(1100,-1000,0),
			new Point(1100,5000,0)).setEmission(new Color(153,153,102))
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	//Two liitle lamps from the 2 sides of piano
	Geometry littleLamp1 = new Polygon(new Point(1000.8,-400,1500),new Point(1000.8,-700,1500),new Point(1000.8,-700,1300),
			new Point(1000.8,-400,1300)).setEmission(new Color(java.awt.Color.BLACK))
			.setMaterial(new Material().setKT(new Double3(0.6)));
	Geometry littleLamp2 = new Polygon(new Point(1000.8,3400,1500),new Point(1000.8,3700,1500),new Point(1000.8,3700,1300),
			new Point(1000.8,3400,1300)).setEmission(new Color(java.awt.Color.BLACK))
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
	Camera camera = new Camera(new Point(-1500, -1500, 800), new Vector(1, 1, 0), new Vector(0,0,1)/*new Vector(1, -1, 0)*/) //
			.setVPSize(2000, 2000).setVPDistance(2000) //
			.setRayTracer(new RayTracerBasic(scene));


	@Test
	void CreatePiano() {
		scene.geometries.add(back,front,rightSide,leftSide,roof,KBfront,KBrightSide,KBleftSide,KBroof,wallBack,wallSide
				,floor,littleLamp1,littleLamp2,
				leg1,leg2,leg3,leg4,seatFloor, seatRoof//,seatEdge1,seatEdge2,seatEdge3,seatEdge4
				/*rightSide.setEmission(new Color(102,51,0)),leftSide.setEmission(new Color(102,51,0)),
				roof.setEmission(new Color(102,51,0))*/);
		scene.lights.add( //
				new SpotLight(new Color(300, 180, 0), new Point(-1000,2500,2500),new Vector(1,-4,-20)) //
						.setKl(1E-6).setKq(1.5E-8));
		scene.lights.add(
				new SpotLight(new Color(300, 180, 0), new Point(1000.9,-500.5,1400),new Vector(1,0,0))
						.setKl(1E-5).setKq(1.5E-7));
		scene.lights.add(
				new SpotLight(new Color(300, 180, 0), new Point(1000.9,3500.5,1400),new Vector(1,0,0))
						.setKl(1E-5).setKq(1.5E-7));
		//scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));


		camera.setImageWriter(new ImageWriter("PianoThread_AntiAlising_33*33", 1000, 1000)) //
				.setAntiAlising(true)//
				//.setSuperSimple(true)//
				//.setDepthOfRec(8)
				.renderImage() //
				.writeToImage();
	}
}

