package org.gogil.chat.server.processor;

import org.gogil.chat.model.*;
import org.gogil.chat.server.db.RoomRepository;
import org.gogil.chat.server.entity.RoomEntity;
import org.gogil.chat.server.room.IRoomManager;
import org.gogil.chat.server.util.JsonUtil;
import org.gogil.chat.server.validator.RoomValidator;
import org.gogil.chat.server.validator.ValidationException;
import org.java_websocket.WebSocket;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CreateRoomProcessor implements IMessageProcessor {
    private final IRoomManager roomManager;
    private final Map<WebSocket, User> connectedUsers;
    private final RoomRepository roomRepository = new RoomRepository();
    private final RoomValidator roomValidator = new RoomValidator();

    public CreateRoomProcessor(IRoomManager roomManager, Map<WebSocket, User> connectedUsers) {
        this.roomManager = roomManager;
        this.connectedUsers = connectedUsers;
    }

    @Override
    public void process(Message message, User sender) {
        String roomName = message.getRoomName();

        try {
            roomValidator.validate(roomName);
        } catch (ValidationException e) {
            Message error = new Message(MessageType.ERROR);
            error.setRoomName(e.getMessage());
            sendToUser(sender, JsonUtil.toJson(error));
            return;
        }

        Room room = roomManager.createRoom(roomName, sender.getUserName());

        if (room == null) {
            Message error = new Message(MessageType.ERROR);
            error.setRoomName("Комната с таким названием уже существует");
            sendToUser(sender, JsonUtil.toJson(error));
            return;
        }

        RoomEntity entity = new RoomEntity(roomName, sender.getUserName());
        roomRepository.save(entity);

        List<String> roomNames = roomManager.getAllRooms().stream()
                .map(Room::getName)
                .collect(Collectors.toList());
        RoomListMessage roomList = new RoomListMessage(roomNames);
        notifyAll(JsonUtil.toJson(roomList));

        roomManager.joinRoom(roomName, sender);
        sender.setCurrentRoom(roomName);
    }

    private void sendToUser(User user, String json) {
        connectedUsers.entrySet().stream()
                .filter(entry -> entry.getValue().getUserName().equals(user.getUserName()))
                .map(Map.Entry::getKey)
                .findFirst()
                .ifPresent(ws -> ws.send(json));
    }

    private void notifyAll(String json) {
        connectedUsers.keySet().forEach(ws -> ws.send(json));
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CREATE_ROOM;
    }
}
