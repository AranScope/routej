import java.util.Scanner;

public class Aran extends Client {

    public Aran(String name, String address, int port) {
        super(name, address, port);

        Scanner sc = new Scanner(System.in);

        while(true){
            String input = sc.nextLine();
            send(input);
        }
    }

    @Override
    void onConnect() {
        System.out.println("Connected to server.");
    }

    @Override
    void onDisconnect() {
        System.out.println("Disconnected from server.");
        System.exit(0);
    }

    @Override
    void onMessageReceived(String message) {
        System.out.println("Message: " + message);
    }

    /**
     * args[0] = name
     * args[1] = address
     * args[3] = port
     * @param args
     */
    public static void main(String[] args){
        new Aran(args[0], args[1], Integer.parseInt(args[2]));
    }
}
