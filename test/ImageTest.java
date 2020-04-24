import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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


    @Test
    public void remove(){
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("b");
        list.add("c");
        list.add("c");
        list.add("c");
        list.add("d");
        list.add("d");
//        removeSome(list);
//        removeSome2(list);
        removeSome3(list);
        for(String s : list){
            System.out.println("s: " + s);
        }
    }

    private void removeSome3(List<String> list) {
        list.removeIf(String -> String.equals("b"));
    }

    private void removeSome2(List<String> list) {
        for (String s : list) {
            if("b".equals(s)){
                list.remove(s);
            }
        }
    }

    private void removeSome(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if("b".equals(s)){
                list.remove(s);
            }

        }
    }



}
