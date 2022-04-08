package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class FriendRequest implements Serializable {
    public static final int WAIT = 0;
    public static final int ACCEPT = 1;
    public static final int REJECT = 2;

    private int id;
    private User sender;
    private User receiver;
    private Timestamp sentTime;
    private int status;

    public FriendRequest() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Timestamp getSentTime() {
        return sentTime;
    }

    public void setSentTime(Timestamp sentTime) {
        this.sentTime = sentTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
