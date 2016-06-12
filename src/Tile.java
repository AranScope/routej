import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tile {
    private BufferedImage image;
    private Point location; //grid location
    private int tileWidth, tileHeight;

    public Tile(String path){
        try {
            image = ResourceLoader.loadImage(path);
        }catch(IOException ex){
        }
    }

    public void draw(Graphics2D g2){
        g2.drawImage(image, location.x * tileWidth, location.x * tileHeight, tileWidth, tileHeight, null);
    }
}
