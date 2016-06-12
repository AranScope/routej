public class GameServer extends Server {
    public GameServer(int port) {
        super(port);
    }

    @Override
    void onMessageReceived(ClientThread client, Object obj) {
        for(ClientThread c: getClients()){
            if(client != c){
                c.send(obj);
            }
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
        log("Server stopping...");
        System.exit(0);
    }

    /**
     * args[0] = port
     * @param args
     */
    public static void main(String[] args){
        new TestServer(Integer.parseInt(args[0]));
    }
}
