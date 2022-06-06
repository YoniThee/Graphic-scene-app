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
class PianoTestOld {
	Color pianoColor = new Color(102,51,0);
	//crate the Resonance box
	Geometry back = new Polygon(new Point(100, 300, 100), new Point(100, 0, 100), new Point(100, 0, 0),
			new Point(100, 300, 0)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry front = new Polygon(new Point(70, 300, 100), new Point(70, 0, 100), new Point(70, 0, 0),
			new Point(70, 300, 0)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry rightSide = new Polygon(new Point(70, 0, 0), new Point(70, 0, 100), new Point(100, 0, 100),
			new Point(100, 0, 0)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry leftSide = new Polygon(new Point(70, 300, 0), new Point(70, 300, 100), new Point(100, 300, 100),
			new Point(100, 300, 0)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry roof = new Polygon(new Point(70, 300, 100), new Point(100, 300, 100), new Point(100, 0, 100),
			new Point(70, 0, 100)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	//crate keyboards
	Geometry KBfront = new Polygon(new Point(40,280,70),new Point(40,280,50),new Point(40,20,50),
			new Point(40,20,70)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry KBrightSide = new Polygon(new Point(40,20,50),new Point(40,20,70),new Point(70,20,80),
			new Point(70,20,50)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry KBleftSide = new Polygon(new Point(40,280,50),new Point(40,280,70),new Point(70,280,80)
			,new Point(70,280,50)).setEmission(pianoColor)
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry KBroof = new Polygon(new Point(40,280,70),new Point(70,280,80),new Point(70,20,80),
			new Point(40,20,70)).setEmission(pianoColor).setMaterial(new Material().setKR(new Double3(0.8)))
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	//carte chair
	Geometry leg1 = new Cylinder(2,new Ray(new Point(30,100,0),new Vector(0,0,1)),40.0)
			.setEmission(new Color(153,102,51))
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry leg2 = new Cylinder(2,new Ray(new Point(0,100,0),new Vector(0,0,1)),40.0)
			.setEmission(new Color(153,102,51))
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry leg3 = new Cylinder(2,new Ray(new Point(30,200,0),new Vector(0,0,1)),40.0)
			.setEmission(new Color(153,102,51));
	Geometry leg4 = new Cylinder(2,new Ray(new Point(0,200,0),new Vector(0,0,1)),40.0)
			.setEmission(new Color(153,102,51))
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry seatFloor = new Polygon(new Point(30,100,40),new Point(0,100,40),new Point(0,200,40),
			new Point(30,200,40)).setEmission(new Color(153,102,51));
	Geometry seatRoof = new Polygon(new Point(30,100,40.5),new Point(0,100,40.5),new Point(0,200,40.5),
			new Point(30,200,40.5))
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
	Geometry wallBack = new Polygon(new Point(110,-100,0),new Point(110,-100,250),new Point(110,500,250),
			new Point(110,500,0)).setEmission(new Color(51,153,102))
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry wallSide = new Polygon(new Point(-200,500,0),new Point(-200,500,250),new Point(110,500,250),
			new Point(110,500,0)).setEmission(new Color(51,153,102))
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	Geometry floor = new Polygon(new Point(-200,500,0),new Point(-200,-100,0),new Point(110,-100,0),
			new Point(110,500,0)).setEmission(new Color(153,153,102))
			.setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setShininess(30));
	//Two liitle lamps from the 2 sides of piano
	Geometry littleLamp1 = new Polygon(new Point(100.8,-40,150),new Point(100.8,-70,150),new Point(100.8,-70,130),
			new Point(100.8,-40,130)).setEmission(new Color(java.awt.Color.BLACK))
			.setMaterial(new Material().setKT(new Double3(0.6)));
	Geometry littleLamp2 = new Polygon(new Point(100.8,340,150),new Point(100.8,370,150),new Point(100.8,370,130),
			new Point(100.8,340,130)).setEmission(new Color(java.awt.Color.BLACK))
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
	Camera camera = new Camera(new Point(-150, -150, 80), new Vector(1, 1, 0), new Vector(0,0,1)/*new Vector(1, -1, 0)*/) //
			.setVPSize(200, 200).setVPDistance(200) //
			.setRayTracer(new RayTracerBasic(scene));
	@Test
	void CreatePiano() {
		scene.geometries.add(back,front,rightSide,leftSide,roof,KBfront,KBrightSide,KBleftSide,KBroof,wallBack,wallSide
				,floor,littleLamp1,littleLamp2,
				leg1,leg2,leg3,leg4,seatFloor, seatRoof//,seatEdge1,seatEdge2,seatEdge3,seatEdge4
				/*rightSide.setEmission(new Color(102,51,0)),leftSide.setEmission(new Color(102,51,0)),
				roof.setEmission(new Color(102,51,0))*/);
		scene.lights.add( //
				new SpotLight(new Color(400, 240, 0), new Point(-100,250,250),new Vector(1,-4,-20)) //
						.setKl(1E-5).setKq(1.5E-7));
		scene.lights.add(
				new SpotLight(new Color(400, 240, 0), new Point(100.9,-50.5,140),new Vector(1,0,0))
						.setKl(1E-5).setKq(1.5E-7));
		scene.lights.add(
				new SpotLight(new Color(400, 240, 0), new Point(100.9,350.5,140),new Vector(1,0,0))
						.setKl(1E-5).setKq(1.5E-7));
	/*	scene.lights.add( //
						new PointLight(new Color(400, 240, 0), new Point(-8,-5,13)) //
						.setKl(1E-5).setKq(1.5E-7));*/
		//scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.15)));

		//camera.setImageWriter(new ImageWriter("Pianozz", 400, 400)); //
		camera.setImageWriter(new ImageWriter("PianoThreadTemp70", 400, 400)) //
				.setAntiAlising(true)
				.setSuperSimple(true)
				.renderImage() //
				.writeToImage();
	}
}