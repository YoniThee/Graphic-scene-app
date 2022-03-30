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
        primitives.Color niceBlue = new Color(40.0,40.0,85.0);
        ImageWriter image = new ImageWriter("testImage",10,16).writePixel(500,800, niceBlue );
    }

    @Test
    /**Test method for {@link ImageWriter.WritePixel}*/
    void testWritePixel() {
    }
}