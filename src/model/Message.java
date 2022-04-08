package model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Message implements Serializable {
    private int id;
    private int roomId;
    private int ownerId;
    private String content;
    private Timestamp createdDate;

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", roomId=" + roomId +
                ", ownerId=" + ownerId +
                ", content='" + content + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
