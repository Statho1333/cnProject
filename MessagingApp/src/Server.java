import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class Server {

    private HashMap<Integer, Account> accounts;
    private AtomicInteger tokenGenerator;


    public Server(int port){

        try {
            accounts = new HashMap<>();
            tokenGenerator = new AtomicInteger(1000);
            System.out.println("Server started at port "+port);
            //create the RMI registry on the port from args
            Registry rmiRegistry = LocateRegistry.createRegistry(port);

            // path to access is rmi://<ip>:<port>/messagingServer
            MessagingServerImp stubServer = new MessagingServerImp(accounts, tokenGenerator);
            rmiRegistry.rebind("messagingServer", stubServer);

        } catch (Exception e) {
            System.out.println("Problem");
        }
    }

    public static void main(String[] args){

        Server server = new Server(Integer.parseInt(args[0]));


    }
}
