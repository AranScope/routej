import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Abstract client representation.
 */
public abstract class Client implements Runnable{
    public String name;
    public String address;
    public int port;

    private Socket socket;
    private DataOutputStream output;
    private DataInputStream input;
    private boolean running = true;

    abstract void onConnect();
    abstract void onDisconnect();
    abstract void onMessageReceived(String message);

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

            output = new DataOutputStream(socket.getOutputStream());
            input = new DataInputStream(socket.getInputStream());

            try{
                Thread.sleep(100);
            }catch(Exception e){

            }
            send(name);

        }catch(IOException ex){
            System.err.println("Client: Failed to connect to server.");
            System.exit(0);
        }
    }

    /**
     * Send a message to the server.
     * @param string
     */
    public void send(String string){
        try {
            output.writeUTF(string);
        }catch(IOException ex){
            System.err.println("Client: Disconnected from server.");
            onDisconnect();
        }
    }

    /**
     * Listen for messages, send events to clients.
     */
    @Override
    public void run(){
        do{
            try {
                String message = input.readUTF();
                onMessageReceived(message);
            }catch(IOException ex){
                System.err.println("Client: Disconnected from server.");
                onDisconnect();
            }
        }while(running);
    }
}
