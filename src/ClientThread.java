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
            name = (String) input.readObject();
            new Thread(this).start();
        } catch (Exception ex) {
            ex.printStackTrace();
            elog("ClientThread: Could not retrieve input stream from socket.");
            running = false;
        }
    }

    /**
     * Check if the current thread is running.
     * @return Whether the current thread is running.
     */
    public boolean isRunning(){
        return running;
    }

    /**
     * Get the address of the connected client.
     * @return The address of the connected client.
     */
    public String getAddress(){
        return socket.getInetAddress().toString();
    }

    /**
     * Get the name of the connected client.
     * @return The name of the connected client.
     */
    public String getName(){
        return name;
    }

    @Override
    public void run() {
        while(running){
            Object inputObject = read();
            if(inputObject != null){
                server.onMessageReceived(this, inputObject);
            }
            else{
                running = false;
                server.onClientDisconnect(this);
            }
        }
    }

    /**
     * Send a message to the client.
     * @param obj The message object.
     */
    public void send(Object obj){
        try {
            output.writeObject(obj);
            output.flush();
        }catch(IOException | ArrayIndexOutOfBoundsException ex){
            running = false;
        }
    }

    /**
     * Retrieve a message from the client.
     * @return Message from the client.
     */
    private Object read(){
        Object inputObject = null;

        try{
            inputObject = input.readObject();
        }catch(Exception ex){
            running = false;
        }

        return inputObject;
    }

    /**
     * Equals method.
     * @param o
     * @return Object equality as unique client threads are equal.
     */
    @Override
    public boolean equals(Object o){
        return o == this;
    }
}
