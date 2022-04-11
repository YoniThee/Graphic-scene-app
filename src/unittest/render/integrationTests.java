package unittest.render;

import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing integration between all the Class
 *
 * @author Dan
 *
 */
class integrationTests {

	 int countIntersections(Geometry shape, Camera camera, int nX, int nY){
		 int counter = 0;
		camera = camera.setVPDistance(1).setVPSize(3,3);
		 for(int i = 0; i < nX; i++)
		 {
			 for(int j = 0; j < nY; j++)
			 {
				 if(shape.findIntsersections(camera.constructRay(3,3,j,i))!=null)
					 counter += shape.findIntsersections(camera.constructRay(3,3,j,i)).size();
			 }
		 }
		 return counter;
	}


	@Test
	void intersectionIntegration() {

		 //*** group tests of integrations between camera to sphere

		//TC01:checking just one ray are intersected the sphere from camera
		Sphere sphere1 = new Sphere(new Point(0,0,-3),1);
		Camera camera1 = new Camera(new Point(0,0,0), new Vector(0,1,0),new Vector(0,0,-1));
		assertEquals(countIntersections(sphere1,camera1, 3, 3),2,
				"BadRay" );

		//TC02: checking all view plan got intersection from camera
		Sphere sphere2 = new Sphere(new Point(0,0,-2.5),2.5);
		Camera camera2 = new Camera(new Point(0,0,0.5), new Vector(0,1,0),new Vector(0,0,-1));
		assertEquals(countIntersections(sphere2, camera2, 3, 3),18, "BadRay");

		//TC03: checking just some rays from camera are intersected sphere
		Sphere sphere3 = new Sphere(new Point(0,0,-2),2);
		Camera camera3 = new Camera(new Point(0,0,0.5), new Vector(0,1,0),new Vector(0,0,-1));
		assertEquals(countIntersections(sphere3, camera3, 3, 3),10, "BadRay");


		//TC04: checking all view plan inside the sphere
		Sphere sphere4 = new Sphere(new Point(0,0,-2),4);
		Camera camera4 = new Camera(new Point(0,0,-1), new Vector(0,1,0),new Vector(0,0,-1));
		assertEquals(countIntersections(sphere4, camera4, 3, 3),9, "BadRay");


		//TC05: checking the camera is look at the other side
		Sphere sphere5 = new Sphere(new Point(0,0,1),0.5);
		Camera camera5 = new Camera(new Point(0,0,0), new Vector(0,1,0),new Vector(0,0,-1));
		assertEquals(countIntersections(sphere5, camera5, 3, 3),0, "BadRay");


		//*** group tests of integrations between camera to plane

		//TC06:  checking the camera got intersections with The parallel plane
		Plane plane6 = new Plane(new Point(0,0,-2),new Vector(0,0,1));
		Camera camera6 = new Camera(new Point(0,0,0), new Vector(0,1,0),new Vector(0,0,-1));
		assertEquals(countIntersections(plane6, camera6, 3, 3), 9, "BadRay");


		//TC07:  checking the camera got intersections with The tilted plane
		Plane plane7 = new Plane(new Point(0,0,-2),new Vector(0,0.1,1));
		Camera camera7 = new Camera(new Point(0,0,1), new Vector(0,1,0),new Vector(0,0,-1));
		assertEquals(countIntersections(plane7, camera7, 3, 3), 9, "BadRay");


		//TC08:  checking the camera got intersections with other tilted plane
		Plane plane8 = new Plane(new Point(0,0,-2),new Vector(0,1,1));
		Camera camera8 = new Camera(new Point(0,0,2), new Vector(0,1,0),new Vector(0,0,-1));
		assertEquals(countIntersections(plane8, camera8, 3, 3), 6, "BadRay");



		//*** group tests of integrations between camera to triangle

		//TC09:  checking the camera got intersections with very small triangle
		Triangle triangle9 = new Triangle(new Point(-1,-1,-2),new Point(1,-1,-2),new Point(0,1,-2));
		Camera camera9 = new Camera(new Point(0,0,2), new Vector(0,1,0),new Vector(0,0,-1));
		assertEquals(countIntersections(triangle9, camera9, 3, 3), 1, "BadRay");


		//TC010:  checking the camera got intersections with very Long and narrow triangle
		Triangle triangle10 = new Triangle(new Point(-1,-1,-2),new Point(1,-1,-2),new Point(0,20,-2));
		Camera camera10 = new Camera(new Point(0,0,2), new Vector(0,1,0),new Vector(0,0,-1));

		assertEquals(countIntersections(triangle10, camera10, 3, 3), 2, "BadRay");





	}
}
