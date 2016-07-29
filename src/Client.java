import java.io.*;
import java.net.Socket;

/**
 * Abstract client representation.
 */
public abstract class Client implements Runnable{
    public String name;
    public String address;
    public int port;

    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private boolean running = true;

    abstract void onConnect();
    abstract void onDisconnect();
    abstract void onMessageReceived(Object obj);

    /**
     * Construct a new client.
     * @param name Name of the client.
     * @param address Address of the server.
     * @param port Port of the server.
     */
    public Client(String name, String address, int port){
        this.name = name;
        this.address = address;
        this.port = port;

        connect(address, port);
        onConnect();

        new Thread(this).start();
    }

    /**
     * Connect to a server.
     * @param address Address of the server.
     * @param port Port of the server.
     */
    private void connect(String address, int port){
        try {
            socket = new Socket(address, port);

            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            send(name);

        }catch(IOException ex){
            System.err.println("Client: Failed to connect to server.");
            //System.exit(0); removed for testing
        }
    }

    /**
     * Send a message to the server.
     * @param obj
     */
    public void send(Object obj){
        try {
            output.writeObject(obj);
            output.flush();
        }catch(IOException | NullPointerException ex){
            System.err.println("Client: Socket closed unexpectedly.");
            running = false;
            onDisconnect();
        }
    }

    /**
     * Disconnect client from server.
     */
    public void disconnect(){
        try {

            socket.close();
        }catch(IOException ex){

        }

        running = false;
        onDisconnect();
    }

    /**
     * Listen for messages, send events to clients.
     */
    @Override
    public void run(){
        do{
            try {
                Object obj = input.readObject();
                onMessageReceived(obj);
            }catch(Exception ex){
                running = false;
                onDisconnect();
            }
        }while(running);
    }
}
