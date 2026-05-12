package org.gogil.chat.server.room;

import org.gogil.chat.model.Room;
import org.gogil.chat.model.User;

import java.util.List;

public interface IRoomManager {
    Room createRoom(String roomName, String createdBy);

    Room findRoom(String roomName);

    List<Room> getAllRooms();

    boolean joinRoom(String roomName, User user);

    void leaveRoom(User user);
}
