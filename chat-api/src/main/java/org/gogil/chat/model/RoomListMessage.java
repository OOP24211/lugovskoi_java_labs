package org.gogil.chat.model;

import java.util.List;

public class RoomListMessage extends Message {

    private List<String> rooms;

    public RoomListMessage() {
        super(MessageType.ROOM_LIST);
    }

    public RoomListMessage(List<String> rooms) {
        super(MessageType.ROOM_LIST);
        this.rooms = rooms;
    }

    public List<String> getRooms() {
        return rooms;
    }

    public void setRooms(List<String> rooms) {
        this.rooms = rooms;
    }
}
