# RouteJ
A simple, event driven, server-client library for Java.

## Basics
When a client connects, they each spawn a new 'ClientThread' on the server. This sends and receives messages to/from the client.

All of this is abstracted away by the ```onMessageReceived(ClientThread t, Object obj)``` and ```send(Object obj)``` methods. As well as helpful methods triggered when the server starts and stops as well as when clients connect and disconnect.

## Usage
A full 'chat room' example is implemented in the 'ChatServer' and 'ChatClient' classes.

If you want to send and receive a single object type, simply 
modify 'Server', 'Client' and 'ClientThread' to be type bound.

## Not using an IDE?
### Here the two classes you'll need
```Java
public class ExampleClient extends Client{

    public ExampleClient(String name, String address, int port) {
        super(name, address, port);
    }

    @Override
    void onConnect() {

    }

    @Override
    void onDisconnect() {

    }

    @Override
    void onMessageReceived(Object obj) {

    }
}
```
```Java
public class ExampleServer extends Server{

    public ExampleServer(int port) {
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

```
