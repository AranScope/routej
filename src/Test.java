import java.util.LinkedList;
import java.util.Random;

/**
 * Created by aranscope on 7/13/16.
 */
public class Test {
    public static void main(String[] args){
        new Test();
    }

    public Test(){
        LinkedList<Client> clients = new LinkedList<>();

        Server server = new Server(4444) {
            @Override
            void onMessageReceived(ClientThread client, Object obj) {
                //log("Client: " + obj);
            }

            @Override
            void onClientConnect(ClientThread client) {
                log("Client: " + client.getName() + " connected.");
            }

            @Override
            void onClientDisconnect(ClientThread client) {
                log("Client: " + client.getName() + " disconnected.");
                log(clients.size() + " clients connected.");
            }

            @Override
            void onStart() {

            }

            @Override
            void onStop() {
                System.out.println("Crashed");
            }
        };

        for(int i = 0; i < 2000; i++){
            clients.add(new Client("" + i, "localhost", 4444) {
                @Override
                void onConnect() {
                }

                @Override
                void onDisconnect() {

                }

                @Override
                void onMessageReceived(Object obj) {

                }
            });
        }

        Random ra = new Random();

        while(true){
            for(Client c: clients){
                try{
                    Thread.sleep(1);
                    c.send("" + c.name);
                }catch(Exception e){}

                c.send("" + System.currentTimeMillis());
            }
            int index = ra.nextInt(clients.size() - 1);
            clients.remove(index).disconnect();

        }
    }
}
