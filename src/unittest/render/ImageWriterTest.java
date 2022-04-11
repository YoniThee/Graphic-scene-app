package unittest.render;

import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point;
import renderer.ImageWriter;
import primitives.*;

import java.awt.*;

import static java.awt.Color.RED;
import static java.awt.Color.YELLOW;
import static org.junit.jupiter.api.Assertions.*;

/*
 * Testing ImageWriter
 * @author Shay Dopelt && Yheonatan Thee
 * */

class ImageWriterTest {
    /**
     * Test method for {@link ImageWriter}.
     */
    @Test
    /**Test method for {@link ImageWriter.WriteToImage}*/
    void testWriteToImage() {
        ImageWriter imageWriter =  new ImageWriter("testImage",800,500);
        for (int i = 0; i <imageWriter.getNx() ; i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                imageWriter.writePixel(i,j, new Color(YELLOW) );
            }
        }
        int interval = 50;
        for (int i = 0; i <imageWriter.getNx() ; i+=interval) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                imageWriter.writePixel(i, j, new Color(RED));
            }
        }
        for (int i = 0; i <imageWriter.getNx() ; i++) {
            for (int j = 0; j < imageWriter.getNy(); j+=interval) {
                imageWriter.writePixel(i, j, new Color(RED));
            }
        }
        imageWriter.writeToImage();
    }

    @Test
    /**Test method for {@link ImageWriter.WritePixel}*/
    void testWritePixel() {
    }
}