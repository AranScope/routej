# RouteJ
A simple, event driven, server-client library for Java.

# Usage
## Basics
To create a client, simply extend the Client class. You will then override the **onConnect()**, **onDisconnect()** and **onMessageReceived** methods.

To create a server, simple extend the Server class. You will then override the **onClientConnect()**, **onClientDisconnect()**, **onStart()**, **onStop()** and **onMessageReceived** methods.
