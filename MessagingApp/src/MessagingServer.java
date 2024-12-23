import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessagingServer extends Remote {

    public String createAccount(String username) throws RemoteException;
    public String showAccounts(int authToken) throws RemoteException;
    public String sendMessage(int authToken, String recipient, String text) throws RemoteException;
    public String showInbox(int authToken) throws RemoteException;
    public String readMessage(int authToken, int messageID) throws RemoteException;
    public String deleteMessage(int authToken, int messageID) throws RemoteException;
    public String executeScenario(String[] args) throws RemoteException;

}
