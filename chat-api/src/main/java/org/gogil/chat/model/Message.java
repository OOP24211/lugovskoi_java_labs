package org.gogil.chat.model;

public class Message {
    private MessageType type;
    private String roomName;
    private String userName;
    private long sentAt;

    public Message() {
        this.sentAt = System.currentTimeMillis();
    }

    public Message(MessageType type) {
        this.type = type;
        this.sentAt = System.currentTimeMillis();
    }

    public Message(MessageType type, String roomName, String userName) {
        this.type = type;
        this.roomName = roomName;
        this.userName = userName;
        this.sentAt = System.currentTimeMillis();
    }

    public MessageType getType() {
        return type;
    }
    public void setType(MessageType type) {
        this.type = type;
    }

    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getSentAt() {
        return sentAt;
    }
    public void setSentAt(long sentAt) {
        this.sentAt = sentAt;
    }
}
