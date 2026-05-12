package org.gogil.chat.server.processor;

import org.gogil.chat.model.*;
import org.gogil.chat.server.db.MessageRepository;
import org.gogil.chat.server.entity.MessageEntity;
import org.gogil.chat.server.room.IRoomManager;
import org.gogil.chat.server.util.JsonUtil;
import org.gogil.chat.server.validator.MessageValidator;
import org.gogil.chat.server.validator.ValidationException;
import org.java_websocket.WebSocket;
import java.util.Map;

public class TextMessageProcessor implements IMessageProcessor {
    private final IRoomManager roomManager;
    private final Map<WebSocket, User> connectedUsers;
    private final MessageRepository messageRepository = new MessageRepository();
    private final MessageValidator messageValidator = new MessageValidator();

    public TextMessageProcessor(IRoomManager roomManager, Map<WebSocket, User> connectedUsers) {
        this.roomManager = roomManager;
        this.connectedUsers = connectedUsers;
    }

    @Override
    public void process(Message message, User sender) {
        TextMessage textMessage = (TextMessage) message;
        String roomName = sender.getCurrentRoom();

        if (roomName == null) return;

        Room room = roomManager.findRoom(roomName);
        if (room == null) return;

        try {
            messageValidator.validate(textMessage.getText());
        } catch (ValidationException e) {
            return;
        }

        textMessage.setUserName(sender.getUserName());
        textMessage.setRoomName(roomName);

        room.addToHistory(textMessage);

        MessageEntity entity = new MessageEntity();
        entity.setRoomName(roomName);
        entity.setUsername(sender.getUserName());
        entity.setContent(textMessage.getText());
        entity.setMessageType("TEXT");
        entity.setSentAt(textMessage.getSentAt());
        messageRepository.save(entity);

        notifyRoom(roomName, JsonUtil.toJson(textMessage));
    }

    private void notifyRoom(String roomName, String json) {
        connectedUsers.entrySet().stream()
                .filter(entry -> roomName.equals(entry.getValue().getCurrentRoom()))
                .forEach(entry -> entry.getKey().send(json));
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.TEXT_MESSAGE;
    }
}
