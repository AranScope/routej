import java.awt.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by aranscope on 6/12/16.
 */
public class Player extends Client implements Serializable, Runnable{
    public static int x, y, width = 50, height = 50;
    private static String name = "";
    private LinkedList<Player> players;
    private Graphics2D g2;

    public Player(String name, String address, int port, int x, int y){
        super(name, address, port);

        this.x = x;
        this.y = y;
        this.name = name;

        players = new LinkedList<>();

        new Thread(this).start();
    }

    public void draw(Graphics2D g2){
        g2.setColor(Color.decode("0xbada55"));
        this.g2 = g2;

        if(x >= 0 && y >= 15) {
            g2.drawString(name, x, y - 15);
            g2.fillRect(x, y, width, height);
        }
    }

    @Override
    void onConnect() {

    }

    @Override
    void onDisconnect() {

    }

    @Override
    void onMessageReceived(Object obj) {
        Player p = (Player) obj;
        System.out.println("Player object received.");
        p.draw(g2);
    }

    @Override
    public void run() {
        while(true) {
            x = MouseInfo.getPointerInfo().getLocation().x;
            y = MouseInfo.getPointerInfo().getLocation().y;
            send(this);
            try{
                Thread.sleep(20);
            }catch(Exception e){

            }
        }
    }
}
