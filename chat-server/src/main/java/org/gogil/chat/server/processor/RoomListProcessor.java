package org.gogil.chat.server.processor;

import org.gogil.chat.model.*;
import org.gogil.chat.server.room.IRoomManager;
import org.gogil.chat.server.util.JsonUtil;
import org.java_websocket.WebSocket;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RoomListProcessor implements IMessageProcessor {
    private final IRoomManager roomManager;
    private final Map<WebSocket, User> connectedUsers;

    public RoomListProcessor(IRoomManager roomManager, Map<WebSocket, User> connectedUsers) {
        this.roomManager = roomManager;
        this.connectedUsers = connectedUsers;
    }

    @Override
    public void process(Message message, User sender) {
        List<String> roomNames = roomManager.getAllRooms().stream()
                .map(Room::getName)
                .collect(Collectors.toList());
        RoomListMessage roomList = new RoomListMessage(roomNames);
        sendToUser(sender, JsonUtil.toJson(roomList));
    }

    private void sendToUser(User user, String json) {
        connectedUsers.entrySet().stream()
                .filter(entry -> entry.getValue() == user)
                .map(Map.Entry::getKey)
                .findFirst()
                .ifPresent(ws -> ws.send(json));
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.ROOM_LIST;
    }
}
