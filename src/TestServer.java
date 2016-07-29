/**
 * Created by aranscope on 7/14/16.
 */
public class TestServer extends Server {
    /**
     * Construct a new server.
     *
     * @param port Port to listen on.
     */
    public TestServer(int port) {
        super(port);
    }

    @Override
    void onMessageReceived(ClientThread client, Object obj) {

    }

    @Override
    void onClientConnect(ClientThread client) {

    }

    @Override
    void onClientDisconnect(ClientThread client) {

    }

    @Override
    void onStart() {

    }

    @Override
    void onStop() {

    }
}
