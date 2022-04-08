package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Friendship implements Serializable {
    public static final int INACTIVE = 0;
    public static final int ACTIVE = 1;

    private int id;
    private User user;
    private User friend;
    private Room room;
    private Timestamp addFriendTime;
    private int status;

    public Friendship() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Timestamp getAddFriendTime() {
        return addFriendTime;
    }

    public void setAddFriendTime(Timestamp addFriendTime) {
        this.addFriendTime = addFriendTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
