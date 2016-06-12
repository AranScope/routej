import java.io.*;
import java.net.Socket;

/**
 * Server thread class. One created per client.
 */
public class ClientThread extends Logger implements Runnable {
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Server server;
    private String name = "";
    private boolean running = true;

    /**
     * Construct a new client communication thread.
     * @param socket
     * @param server
     */
    public ClientThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;

        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            name = input.readUTF();
        } catch (IOException ex) {
            ex.printStackTrace();
            elog("ClientThread: Could not retrieve input stream from socket.");
            server.onClientDisconnect(this);
            running = false;
        }
    }

    //trx
    public boolean isRunning(){
        return running;
    }

    public String getAddress(){
        return socket.getInetAddress().toString();
    }

    public String getName(){
        return name;
    }

    @Override
    public void run() {
        while(running){
            Object inputObject = read();
            if(inputObject != null) server.onMessageReceived(this, inputObject);
        }
    }

    public void send(Object obj){
        try {
            output.writeObject(obj);
            output.flush();
        }catch(IOException ex){
            server.getClients().remove(this);
            server.onClientDisconnect(this);
            running = false;
        }
    }

    private Object read(){
        Object inputObject = null;

        try{
            inputObject = input.readObject();
        }catch(Exception ex){
            server.onClientDisconnect(this);
            running = false;
        }

        return inputObject;
    }

    @Override
    public boolean equals(Object o){
        return false;
    }
}
