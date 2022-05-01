package unittest.render;

import org.junit.jupiter.api.Test;

import lighting.AmbientLight;
import geometries.*;
import org.xml.sax.SAXException;
import primitives.*;
import renderer.*;
import scene.Scene;
import renderer.RayTracerBasic;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import static java.awt.Color.*;

/**
 * Test rendering a basic image
 * 
 * @author Dan
 */
public class RenderTests {

	/**
	 * Produce a scene with basic 3D model and render it into a png image with a
	 * grid
	 */
	/*@Test
	public void basicRenderTwoColorTest() {
		Scene scene = new Scene("Test scene")//
				.setAmbientLight(new AmbientLight(new Color(255, 191, 191), //
						                          new Double3(1,1,1))) //
				.setBackground(new Color(75, 127, 90));

		scene.geometries.add(new Sphere( new Point(0, 0, -100),50),
				new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100),
						new Point(-100, 100, -100)), // up
																													// left
				new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100),
						new Point(-100, -100, -100)), // down
																														// left
				new Triangle(new Point(100, 0, -100), new Point(0, -100, -100),
						new Point(100, -100, -100))); // down
																													// right
		Camera camera = new Camera(new Point(0,0,-200), new Vector(0, -1, 0), new Vector(0, 0, 1)) //
				.setVPDistance(100) //
				.setVPSize(500, 500) //
				.setImageWriter(new ImageWriter("base render test old", 1000, 1000))
				.setRayTracer(new RayTracerBasic(scene));

		camera.renderImage();
		camera.printGrid(100, new Color(java.awt.Color.YELLOW));
		camera.writeToImage();
	}*/
	/////////////////////////this is the new test of Targil6
	@Test
	public void basicRenderMultiColorTest() {
		Scene scene = new Scene("Test scene")//
				.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.2)));//

		scene.geometries.add( //
				new Sphere(new Point(0, 0, -100), 50),
				// up left
				new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100))
						.setEmission(new Color(GREEN)),
				// down left
				new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100))
						.setEmission(new Color(RED)),
				// down right
				new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))
						.setEmission(new Color(BLUE)));

		Camera camera = new Camera(new Point(0,0,-200), new Vector(0, -1, 0), new Vector(0, 0, 1)) //
				.setVPDistance(100) //
				.setVPSize(500, 500) //
				.setImageWriter(new ImageWriter("color render test", 1000, 1000))
				.setRayTracer(new RayTracerBasic(scene));

		camera.renderImage();
		camera.printGrid(100, new Color(WHITE));
		camera.writeToImage();
	}



	/**
	 * Test for XML based scene - for bonus
	 */
	@Test
	public void basicRenderXml() throws ParserConfigurationException, IOException, SAXException {
		/*Scene scene = new Scene("XML Test scene");
		// enter XML file name and parse from XML file into scene object
		// ...
		//File xmlFile = new File("basicRenderTestTwoColors.xml");
		//DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//DocumentBuilder builder = factory.newDocumentBuilder();
		//Document doc = (Document) builder.parse(xmlFile);

		//doc.getDocumentElement();

		Camera camera = new Camera(new Point(0,0,0), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPDistance(100) //
				.setVPSize(500, 500)
				.setImageWriter(new ImageWriter("xml render test", 1000, 1000))
				.setRayTracer(new RayTracerBasic(scene));
		camera.renderImage();
		camera.printGrid(100, new Color(java.awt.Color.YELLOW));
		camera.writeToImage();*/
	}
}
