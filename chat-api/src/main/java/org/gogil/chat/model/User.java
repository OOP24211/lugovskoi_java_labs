package org.gogil.chat.model;

public class User {
    private String userName;
    private String currentRoom;
    private long connectedAt;

    public User() {
        this.connectedAt = System.currentTimeMillis();
    }

    public User(String userName) {
        this.userName = userName;
        this.connectedAt = System.currentTimeMillis();
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCurrentRoom() {
        return currentRoom;
    }
    public void setCurrentRoom(String currentRoom) {
        this.currentRoom = currentRoom;
    }

    public long getConnectedAt() {
        return connectedAt;
    }
    public void setConnectedAt(long connectedAt) {
        this.connectedAt = connectedAt;
    }
}
