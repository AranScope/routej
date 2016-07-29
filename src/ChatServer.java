import java.util.Scanner;

/**
 * Test implementation of server.
 */
public class ChatServer extends Server {
    public ChatServer(int port) {
        super(port);

        Scanner sc = new Scanner(System.in);

        while(true){
            String input = sc.nextLine();
            switch (input) {
                case "who":
                    String clients = "";
                    for (ClientThread thread : getClients()) {
                        clients += thread.getName();
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
    void onMessageReceived(ClientThread client, Object obj) {
        String message = (String) obj;
        log("Message from " + client.getName() + ": " + message);

        for(ClientThread c: getClients()){
            if(client != c){
                c.send(client.getName() + ": " + message);
            }
        }

        if(message.equals("list")){
            String clients = "";
            for(ClientThread c: getClients()){
                clients += c.getName() + ",";
            }
            client.send(clients);
        }

        log("clients: " + this.getClients().size());
    }

    @Override
    void onClientConnect(ClientThread client) {
        log("Client " + client.getName() + " connected with address: " + client.getAddress());
    }

    @Override
    void onClientDisconnect(ClientThread client) {
        getClients().remove(client);
        log("Client " + client.getName() + " disconnected with address: " + client.getAddress());
    }

    @Override
    void onStart() {
        log("Server running on port " + getPort());
        log("Type 'help' for the command reference");
    }

    @Override
    void onStop() {
        log("Server stopping...");
        System.exit(0);
    }

    /**
     * args[0] = port
     * @param args
     */
    public static void main(String[] args){
        new ChatServer(Integer.parseInt(args[0]));
    }
}
