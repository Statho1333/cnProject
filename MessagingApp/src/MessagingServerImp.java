import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MessagingServerImp extends UnicastRemoteObject implements MessagingServer {

    private HashMap<Integer, Account> accounts;
    private AtomicInteger tokenGenerator;

    protected MessagingServerImp(HashMap<Integer,Account> accounts, AtomicInteger tokenGenerator) throws RemoteException {
        super();
        this.accounts = new HashMap<>();
        this.tokenGenerator = tokenGenerator;
    }


    /**
     * Checks the id and selects which scenario to execute
     * @param args is the args from the client terminal
     * @return a String, which will be generated from the scenario that is selected
     * @throws RemoteException
     */
    public String executeScenario (String[] args) throws RemoteException{

        int id = Integer.parseInt(args[2]);
        String toReturn=null;

        switch (id){
            case 1:
                toReturn = createAccount(args[3]);
                break;
            case 2:
                toReturn = showAccounts(Integer.parseInt(args[3]));
                break;
            case 3:
                toReturn = sendMessage(Integer.parseInt(args[3]), args[4], args[5]);
                break;
            case 4:
                toReturn = showInbox(Integer.parseInt(args[3]));
                break;
            case 5:
                toReturn = readMessage(Integer.parseInt(args[3]),Integer.parseInt(args[4]));
                break;
            case 6:
                toReturn = deleteMessage(Integer.parseInt(args[3]),Integer.parseInt(args[4]));
                break;
            default:
                toReturn = "No matching id, service terminated";
        }

        return toReturn;

    }

    /**
     * Creates an account if conditions are met, no duplicate usernames allowed, no usernames with symbols allowed
     * @param username is the argument passed to server side from the terminal of client
     * @return error message if action not allowed, the authToken of the user if account created successfully
     * @throws RemoteException
     */
    @Override
    public synchronized String createAccount(String username) throws RemoteException {

        //checks for the username to be valid (not containing symbols)
        if (!GeneralMethods.onlyLetters(username)){
            return "Invalid Username";
        }
        //checks if there is a username with this username stored
        for (Account account : accounts.values()){
            if (account.getUsername().equals(username)){
                return "Sorry, the user already exists";
            }
        }
       //creates the account
        Account account = new Account(username,tokenGenerator.getAndIncrement());
        accounts.put(account.getAuthToken(),account);
        return "" + account.getAuthToken();


    }

    /**
     * Prints the usernames of all acounts in the terminal,
     * @param authToken is the token of the user that is using the command
     * @return A string with the usernames listed.
     * @throws RemoteException
     */
    @Override
    public synchronized String showAccounts(int authToken) throws RemoteException {
        String toReturn= "";
        if (accounts.containsKey(authToken)){
            int counter = 1;
            for (Map.Entry<Integer,Account> entry : accounts.entrySet()){
                toReturn += counter + "." + " " + entry.getValue().getUsername() + "\n";
                counter++;
            }
        } else {
            toReturn = "invalid authToken";
        }
        return toReturn;

    }

    /**
     * Sends a message from an account to another account
     * @param authToken is the token of the sender
     * @param recipient is the username of the receiver
     * @param text is the "body" of the message
     * @return "Invalid token" if sender does not exist, "OK" if message sent successfully, "User does not exist" if recipient does not exist at all
     * @throws RemoteException
     */
    @Override
    public synchronized String sendMessage(int authToken, String recipient, String text) throws RemoteException {

        //case sender doesnt exist
        if (!accounts.containsKey(authToken)){
            return "Invalid Auth Token";
        }


        String sender = accounts.get(authToken).getUsername();
        //try to find the receiver
        for (Map.Entry<Integer,Account> entry : accounts.entrySet()){
            if (entry.getValue().getUsername().equals(recipient)){

                //receiver found, create the new message and add it to the receiver messageList
                Message message = new Message(sender,recipient,text);
                entry.getValue().addMessage(message);
                return "OK";

            }
        }
        //case recipient does not exist at all
        return "User does not exist";
    }

    /**
     * Show the inbox of the user
     * @param authToken is the account that wants to check its inbox
     * @return "invalid auth Token" if token doesn't exist, the incoming messages otherwise
     * @throws RemoteException
     */
    @Override
    public String showInbox(int authToken) throws RemoteException {
        //case token doesnt exist
        if (!accounts.containsKey(authToken)){
            return "Invalid Auth token";
        }

        return accounts.get(authToken).printMessages();

    }

    /**
     * Prints the sender and the body of a message
     * @param authToken is the token of the user
     * @param messageID is the message id that needs to be read
     * @return "Invalid auth token" if user doesn't exist, otherwise the sender and the body of the message.
     * @throws RemoteException
     */
    @Override
    public synchronized String readMessage(int authToken, int messageID) throws RemoteException {
        if (!accounts.containsKey(authToken)){
            return "Invalid Auth token";
        }
        return accounts.get(authToken).readMessage(messageID);
    }

    /**
     * Deletes the message with the given id from a user
     * @param authToken the token of the user
     * @param messageID the message to be deleted
     * @return "invalid auth token" if user does not exist, otherwise the corresponding message.
     * @throws RemoteException
     */
    public String deleteMessage(int authToken, int messageID) throws RemoteException{
        if (!accounts.containsKey(authToken)){
            return "Invalid Auth token";
        }
        return accounts.get(authToken).deleteMessage(messageID);
    }
}
