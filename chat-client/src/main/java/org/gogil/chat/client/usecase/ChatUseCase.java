package org.gogil.chat.client.usecase;

import org.gogil.chat.client.websocket.ChatWebSocketClient;
import org.gogil.chat.model.FileMessage;
import org.gogil.chat.model.Message;
import org.gogil.chat.model.MessageType;
import org.gogil.chat.model.TextMessage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class ChatUseCase {

    private final ChatWebSocketClient client;

    public ChatUseCase(ChatWebSocketClient client) {
        this.client = client;
    }

    public void joinRoom(String roomName) {
        Message message = new Message(MessageType.JOIN_ROOM, roomName, null);
        client.sendMessage(message);
    }

    public void leaveRoom(String roomName) {
        Message message = new Message(MessageType.LEAVE_ROOM, roomName, null);
        client.sendMessage(message);
    }

    public void sendMessage(String roomName, String userName, String text) {
        TextMessage message = new TextMessage(roomName, userName, text);
        client.sendMessage(message);
    }

    public void createRoom(String roomName) {
        Message message = new Message(MessageType.CREATE_ROOM, roomName, null);
        client.sendMessage(message);
    }

    public void requestRoomList() {
        Message message = new Message(MessageType.ROOM_LIST, null, null);
        client.sendMessage(message);
    }

    public void sendFile(String roomName, String userName, File file) throws IOException {
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        String base64Content = Base64.getEncoder().encodeToString(fileBytes);
        String fileType = Files.probeContentType(file.toPath());
        if (fileType == null) fileType = "application/octet-stream";

        FileMessage message = new FileMessage(roomName, userName,
                file.getName(), fileType, base64Content, file.length());
        client.sendMessage(message);
    }
}
