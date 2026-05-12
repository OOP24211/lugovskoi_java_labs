package org.gogil.chat.model;

import java.util.List;

public class UserListMessage extends Message {

    private List<String> users;

    public UserListMessage() {
        super(MessageType.USER_LIST);
    }

    public UserListMessage(String roomName, List<String> users) {
    super(MessageType.USER_LIST);
    setRoomName(roomName);
    this.users = users;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
