import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Account implements Serializable {
    private String username;
    private int authToken;
    private List<Message> messageBox;
    int messageCounter;



    public Account(String username, int authToken) {
        this.messageBox = new ArrayList<>();
        this.username = username;
        this.authToken = authToken;
        this.messageCounter = 0;
    }

    public int getAuthToken() {
        return authToken;
    }

    public void setAuthToken(int authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }




    /**
     * Adds a message to the messageBox
     * @param message is the message that will be inserted
     * @return true if message added successfully, false otherwise
     */
    public boolean addMessage(Message message){
        try {
            messageBox.add(message);
            messageCounter++;
            message.setMessageID(messageCounter);
            return true;
        }
        catch (Exception e){
            return false;
        }

    }

    /**
     * Iterates in messageBox and tries to find the message with the given id
     * @param id is the message id we are searching for
     * @return "OK" if message found and deleted successfully, error message otherwise
     */
    public String deleteMessage(int id){
        Iterator<Message> iterator = messageBox.iterator();
        while (iterator.hasNext()){
            Message message = iterator.next();
            if (message.getMessageID() == id){
                iterator.remove();
                return "OK";
            }
        }
        return "Message does not exist";
    }

    /**
     * Prints all the messages in format (<message_id>"." "from:"<username_sernder> plus an "*" if message is not read)
     * @return a list of all the messages
     */
    public String printMessages(){
        String toReturn="";
        for (Message message : messageBox){
            String isRead = message.isRead() ? "" : "*";
            toReturn += message.getMessageID() + ". from: " + message.getSender() + isRead +  "\n";
        }
        return toReturn;
    }

    /**
     * Prints the receiver and the body of the message. Sets the message to isRead to true
     * @param id is the id of the message
     * @return id does not exist prints "Message ID does not exist", otherwise prints the sender and the body of the message
     */
    public String readMessage(int id){
        for (Message message : messageBox){
            if (message.getMessageID()==id){
                message.setRead(true);
                return "(" + message.getSender() + ")" + " " + message.getBody();
            }
        }
        return "Message ID does not exist";
    }

}
