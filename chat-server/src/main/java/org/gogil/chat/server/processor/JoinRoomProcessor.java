package org.gogil.chat.server.processor;

import org.gogil.chat.model.*;
import org.gogil.chat.server.db.MessageRepository;
import org.gogil.chat.server.entity.MessageEntity;
import org.gogil.chat.server.room.IRoomManager;
import org.gogil.chat.server.util.JsonUtil;
import org.java_websocket.WebSocket;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JoinRoomProcessor implements IMessageProcessor {
    private final IRoomManager roomManager;
    private final Map<WebSocket, User> connectedUsers;
    private final MessageRepository messageRepository = new MessageRepository();

    public JoinRoomProcessor(IRoomManager roomManager, Map<WebSocket, User> connectedUsers) {
        this.roomManager = roomManager;
        this.connectedUsers = connectedUsers;
    }

    @Override
    public void process(Message message, User sender) {
        String roomName = message.getRoomName();

        if (roomName == null) return;
        if (roomName.equals(sender.getCurrentRoom())) return;

        boolean joined = roomManager.joinRoom(roomName, sender);
        if (!joined) return;

        sender.setCurrentRoom(roomName);

        List<MessageEntity> history = messageRepository.getHistory(roomName, 100);
        List<String> rawMessages = history.stream()
                .map(this::entityToJson)
                .collect(Collectors.toList());

        HistoryMessage historyMessage = new HistoryMessage(roomName, rawMessages);
        sendToUser(sender, JsonUtil.toJson(historyMessage));

        Message joinMessage = new Message(MessageType.USER_JOIN, roomName, sender.getUserName());
        notifyRoom(roomName, JsonUtil.toJson(joinMessage));

        Room room = roomManager.findRoom(roomName);
        List<String> userNames = room.getUsers().stream()
                .map(User::getUserName)
                .collect(Collectors.toList());
        UserListMessage userList = new UserListMessage(roomName, userNames);
        notifyRoom(roomName, JsonUtil.toJson(userList));
    }

    private String entityToJson(MessageEntity entity) {
        if ("TEXT".equals(entity.getMessageType())) {
            TextMessage msg = new TextMessage(entity.getRoomName(), entity.getUsername(), entity.getContent());
            msg.setSentAt(entity.getSentAt());
            return JsonUtil.toJson(msg);
        } else if ("FILE".equals(entity.getMessageType())) {
            FileMessage msg = new FileMessage(entity.getRoomName(), entity.getUsername(),
                    entity.getFileName(), entity.getFileType(),
                    entity.getFileContent(), entity.getFileSize());
            msg.setSentAt(entity.getSentAt());
            return JsonUtil.toJson(msg);
        }
        return null;
    }

    private void sendToUser(User user, String json) {
        connectedUsers.entrySet().stream()
                .filter(entry -> entry.getValue().getUserName().equals(user.getUserName()))
                .map(Map.Entry::getKey)
                .findFirst()
                .ifPresent(socket -> socket.send(json));
    }

    private void notifyRoom(String roomName, String json) {
        connectedUsers.entrySet().stream()
                .filter(entry -> roomName.equals(entry.getValue().getCurrentRoom()))
                .forEach(entry -> entry.getKey().send(json));
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.JOIN_ROOM;
    }
}
