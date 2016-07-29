import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Abstract server representation for object messaging.
 */
public abstract class Server extends Logger implements Runnable{
    boolean running = true;
    private LinkedBlockingQueue<ClientThread> clients;
    private int port;

    abstract void onMessageReceived(ClientThread client, Object obj);
    abstract void onClientConnect(ClientThread client);
    abstract void onClientDisconnect(ClientThread client);
    abstract void onStart();
    abstract void onStop();

    /**
     * Get the port of the server.
     * @return Port of the server.
     */
    public int getPort(){
        return port;
    }

    /**
     * Get a list of all connected clients.
     * @return List of connected clients.
     */
    public LinkedBlockingQueue<ClientThread> getClients(){
        return clients;
    }

    /**
     * Send a message to all connected clients.
     * @param message Message.
     */
    public void broadcast(Object message){
        for(ClientThread c: clients){
            c.send(message);
        }
    }

    /**
     * Stop the server from running.
     */
    public void stop(){
        running = false;
    }

    /**
     * Construct a new server.
     * @param port Port to listen on.
     */
    public Server(int port){
        this.port = port;
        clients = new LinkedBlockingQueue<>();
        new Thread(this).start();
    }
    
    /**
     * Listen for messages, send events to clients.
     */
    @Override
    public void run(){
        ServerSocket s = null;

        try{
            s = new ServerSocket(port);
            onStart();
        }catch(IOException ex){
            elog("Server: Failed to create server socket");
            onStop();
        }

        do{
            try {
                Socket clientSocket = s.accept();
                ClientThread client = new ClientThread(clientSocket, this);
                if(client.isRunning()) {
                    clients.add(client);
                    onClientConnect(client);
                }

            } catch(IOException | NullPointerException ex){
                elog("Server: Server socket error on client connect");
                elog(ex.toString());
                onStop();
            }

        }while(running);

        onStop();
    }
}
