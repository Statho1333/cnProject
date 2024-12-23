import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class Client {

    public static void main(String[] args){
        try {
            System.out.println(args.length);
            if (args.length < 3){
                System.out.println("Format must be: java client <ip> <port number> <FN_ID> <args>");
                return;
            }

            String ip = args[0];
            int port = Integer.parseInt(args[1]);

            //connect to the RMI registry
            Registry rmiRegistry = LocateRegistry.getRegistry(ip, port);

            //get reference for remote object and use its functions
            MessagingServer stub = (MessagingServer) rmiRegistry.lookup("messagingServer");
            System.out.println("Client connected");
            System.out.println(stub.executeScenario(args));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
