package org.gogil.chat.server.processor;

import org.gogil.chat.model.*;
import org.gogil.chat.server.db.MessageRepository;
import org.gogil.chat.server.entity.MessageEntity;
import org.gogil.chat.server.room.IRoomManager;
import org.gogil.chat.server.util.JsonUtil;
import org.java_websocket.WebSocket;
import java.util.Map;

public class FileMessageProcessor implements IMessageProcessor {
    private final IRoomManager roomManager;
    private final Map<WebSocket, User> connectedUsers;
    private final MessageRepository messageRepository = new MessageRepository();

    public FileMessageProcessor(IRoomManager roomManager, Map<WebSocket, User> connectedUsers) {
        this.roomManager = roomManager;
        this.connectedUsers = connectedUsers;
    }

    @Override
    public void process(Message message, User sender) {
        FileMessage fileMessage = (FileMessage) message;
        String roomName = sender.getCurrentRoom();

        if (roomName == null) return;

        Room room = roomManager.findRoom(roomName);
        if (room == null) return;

        fileMessage.setUserName(sender.getUserName());
        fileMessage.setRoomName(roomName);

        room.addToHistory(fileMessage);

        MessageEntity entity = new MessageEntity();
        entity.setRoomName(roomName);
        entity.setUsername(sender.getUserName());
        entity.setMessageType("FILE");
        entity.setFileName(fileMessage.getFileName());
        entity.setFileType(fileMessage.getFileType());
        entity.setFileContent(fileMessage.getFileContent());
        entity.setFileSize(fileMessage.getFileSize());
        entity.setSentAt(fileMessage.getSentAt());
        messageRepository.save(entity);

        notifyRoom(roomName, JsonUtil.toJson(fileMessage));
    }

    private void notifyRoom(String roomName, String json) {
        connectedUsers.entrySet().stream()
                .filter(entry -> roomName.equals(entry.getValue().getCurrentRoom()))
                .forEach(entry -> entry.getKey().send(json));
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.FILE_MESSAGE;
    }
}
