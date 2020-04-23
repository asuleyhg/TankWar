import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ImageTest {
    @Test
    public void testLoadImage(){

        try {
            BufferedImage tankU = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("images/p1tankU.gif"));
//            tankU =
            assertNotNull(tankU);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
