import java.io.*;
import java.net.Socket;

/**
 * Server thread class. One created per client.
 */
public class ClientThread extends Logger implements Runnable {
    private Socket socket;
    private DataOutputStream output;
    private DataInputStream input;
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
            output = new DataOutputStream(socket.getOutputStream());
            input = new DataInputStream(socket.getInputStream());
            name = input.readUTF();
            log(name);
        } catch (IOException ex) {
            elog("ClientThread: Could not retrieve input stream from socket.");
            server.onClientDisconnect(this);
            running = false;
        }
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
            String inputString = read();
            if(inputString != null) server.onMessageReceived(this, inputString);
        }
    }

    public void send(String string){
        try {
            output.writeUTF(string);
        }catch(IOException ex){
            server.getClients().remove(this);
            server.onClientDisconnect(this);
            running = false;
        }
    }

    private String read(){
        String inputString = null;

        try{
            inputString = input.readUTF();
        }catch(IOException ex){
            server.onClientDisconnect(this);
            running = false;
        }

        return inputString;
    }

    @Override
    public boolean equals(Object o){
        return false;
    }
}
