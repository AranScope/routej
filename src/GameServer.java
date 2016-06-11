/**
 * Test implementation of server.
 */
public class GameServer extends Server {
    public GameServer(int port) {
        super(port);
    }

    @Override
    void onMessageReceived(ClientThread client, String message) {
        log("Message from " + client.getAddress() + ": " + message);

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
    }

    @Override
    void onStop() {

    }

    /**
     * args[0] = port
     * @param args
     */
    public static void main(String[] args){
        new GameServer(Integer.parseInt(args[0]));
    }
}
