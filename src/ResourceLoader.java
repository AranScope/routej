import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResourceLoader {

    public static BufferedImage loadImage(String path) throws IOException{
        return ImageIO.read(new File(path));
    }

    public static Clip loadClip(String path) throws IOException{
        File audioFile = new File(path).getAbsoluteFile();
        Clip clip = null;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        }catch(Exception ex){
        }

        return clip;
    }
}
