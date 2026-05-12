package org.gogil.chat.server.processor;

import org.gogil.chat.model.*;
import org.gogil.chat.server.room.IRoomManager;
import org.gogil.chat.server.util.JsonUtil;
import org.java_websocket.WebSocket;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LeaveRoomProcessor implements IMessageProcessor {
    private final IRoomManager roomManager;
    private final Map<WebSocket, User> connectedUsers;

    public LeaveRoomProcessor(IRoomManager roomManager, Map<WebSocket, User> connectedUsers) {
        this.roomManager = roomManager;
        this.connectedUsers = connectedUsers;
    }

    @Override
    public void process(Message message, User sender) {
        String roomName = sender.getCurrentRoom();
        if (roomName == null) return;
        if (sender.getUserName() == null) return;

        Message leaveMessage = new Message(MessageType.USER_LEAVE, roomName, sender.getUserName());
        notifyRoom(roomName, JsonUtil.toJson(leaveMessage));

        roomManager.leaveRoom(sender);

        Room room = roomManager.findRoom(roomName);
        if (room != null) {
            List<String> userNames = room.getUsers().stream()
                    .map(User::getUserName)
                    .collect(Collectors.toList());
            UserListMessage userList = new UserListMessage(roomName, userNames);
            notifyRoom(roomName, JsonUtil.toJson(userList));
        }
    }

    private void notifyRoom(String roomName, String json) {
        connectedUsers.entrySet().stream()
                .filter(entry -> roomName.equals(entry.getValue().getCurrentRoom()))
                .filter(entry -> entry.getKey().isOpen())
                .forEach(entry -> entry.getKey().send(json));
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.LEAVE_ROOM;
    }
}
