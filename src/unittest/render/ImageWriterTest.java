package unittest.render;

import org.junit.jupiter.api.Test;
import primitives.Color;
import renderer.ImageWriter;
import primitives.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    /**Test method for {@link ImageWriter.WriteToImage}*/
    void testWriteToImage() {
        primitives.Color niceBlue = new primitives.Color(0.0,0.0,85.0);
        ImageWriter image =  new ImageWriter("testImage",500,800);
        image.writePixel(10,16, niceBlue );
        image.writeToImage();
    }

    @Test
    /**Test method for {@link ImageWriter.WritePixel}*/
    void testWritePixel() {
    }
}