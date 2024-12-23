import java.io.Serializable;

public class Message implements Serializable {
    private boolean isRead;
    private String sender;
    private String receiver;
    private String body;
    private int messageID;

    public Message(String sender, String receiver, String body){
        this.isRead = false;
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
    }

    public boolean isRead() {
        return isRead;
    }

    public String getBody() {
        return body;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }
}
