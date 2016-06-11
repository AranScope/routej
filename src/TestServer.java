import java.util.Scanner;

/**
 * Test implementation of server.
 */
public class TestServer extends Server {
    public TestServer(int port) {
        super(port);

        Scanner sc = new Scanner(System.in);

        while(true){
            String input = sc.nextLine();
            switch (input) {
                case "who":
                    String clients = "";
                    for (ClientThread thread : getClients()) {
                        clients += thread.getAddress();
                    }
                    log(clients);
                    break;
                case "help":
                    log("'who' - See the list of connected users.");
                    log("'kick 'user' - Kick a user from the server.");
                    break;
            }
        }
    }

    @Override
    void onMessageReceived(ClientThread client, String message) {
        log("Message from " + client.getName() + ": " + message);

        for(ClientThread c: getClients()){
            c.send(client.getName() + ": " + message);
        }

        if(message.equals("list")){
            String clients = "";
            for(ClientThread c: getClients()){
                clients += c.getName() + ",";
            }
            client.send(clients);
        }
    }

    @Override
    void onClientConnect(ClientThread client) {
        log("Client " + client.getName() + " connected with address: " + client.getAddress());
    }

    @Override
    void onClientDisconnect(ClientThread client) {
        log("Client " + client.getName() + " disconnected with address: " + client.getAddress());
    }

    @Override
    void onStart() {
        log("Server running on port " + getPort());
        log("Type 'help' for the command reference");
    }

    @Override
    void onStop() {

    }

    /**
     * args[0] = port
     * @param args
     */
    public static void main(String[] args){
        new TestServer(Integer.parseInt(args[0]));
    }
}
