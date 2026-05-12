package org.gogil.chat.server.room;

import org.gogil.chat.model.Room;
import org.gogil.chat.model.User;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RoomManagerImplementation implements IRoomManager{
    private final Map<String, Room> rooms = new ConcurrentHashMap<>();

    public Room createRoom(String roomName, String createdBy) {
        if (rooms.containsKey(roomName)){
            return null;
        }
        Room room = new Room(roomName, createdBy);
        rooms.put(roomName, room);
        return room;
    }

    public Room findRoom(String roomName) {
        return rooms.get(roomName);
    }

    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms.values());
    }

    public boolean joinRoom(String roomName, User user) {
        Room room = findRoom(roomName);
        if (room == null){
            return false;
        }
        room.addUser(user);
        return true;

    }

    public void leaveRoom(User user) {
        String roomName = user.getCurrentRoom();
        if (roomName == null) {
            return;
        }
        Room room = findRoom(roomName);
        if (room == null) {
            return;
        }
        room.removeUser(user.getUserName());
        user.setCurrentRoom(null);
    }
}
