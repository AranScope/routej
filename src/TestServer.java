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
                    String ref = "'who' - See the list of connected users.\n" +
                                  "'kick 'user' - Kick a user from the server.\n";

                    log(ref);
                    break;
            }
        }
    }

    @Override
    void onMessageReceived(ClientThread client, String message) {
        log("Message from " + client.getAddress() + ": " + message);

        for(ClientThread c: getClients()){
            c.send(message);
        }

        if(message.equals("list")){
            String clients = "";
            for(ClientThread c: getClients()){
                clients += c.getAddress() + ",";
            }
            client.send(clients);
        }
    }

    @Override
    void onClientConnect(ClientThread client) {
        log("Client connected with address: " + client.getAddress());
    }

    @Override
    void onClientDisconnect(ClientThread client) {
        log("Client disconnected with address: " + client.getAddress());
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
