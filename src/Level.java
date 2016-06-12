import javax.swing.*;
import java.awt.*;

public class Level extends JPanel{
    private static Player player;

    public Level(){
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);

        while(true){
            frame.repaint();
            this.repaint();
            try{
                Thread.sleep(20);
            }catch(Exception e){
            }
        }
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        player.draw(g2);
    }

    /**
     * args[0] = name
     * args[1] = address
     * args[3] = port
     * @param args
     */
    public static void main(String[] args){
        player = new Player(args[0], args[1], Integer.parseInt(args[2]), 0, 0);
        new Level();
    }
}
