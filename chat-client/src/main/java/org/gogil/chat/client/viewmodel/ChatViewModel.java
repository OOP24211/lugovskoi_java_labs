package org.gogil.chat.client.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gogil.chat.client.usecase.ChatUseCase;
import org.gogil.chat.client.websocket.ChatWebSocketClient;
import org.gogil.chat.model.*;

import java.io.File;
import java.io.IOException;

public class ChatViewModel {

    private final ChatUseCase chatUseCase;
    private final String currentUserName;

    public final ObservableList<String> rooms = FXCollections.observableArrayList();
    public final ObservableList<Message> messages = FXCollections.observableArrayList();
    public final ObservableList<String> users = FXCollections.observableArrayList();
    public final StringProperty errorText = new SimpleStringProperty("");

    private String currentRoom = null;

    public ChatViewModel(ChatWebSocketClient client, String userName) {
        this.currentUserName = userName;
        this.chatUseCase = new ChatUseCase(client);
        client.setOnMessageReceived(this::handleMessage);
        chatUseCase.requestRoomList();
    }

    public void joinRoom(String roomName) {
        if (roomName.equals(currentRoom)) return;
        if (currentRoom != null) {
            chatUseCase.leaveRoom(currentRoom);
        }
        currentRoom = roomName;
        messages.clear();
        users.clear();
        chatUseCase.joinRoom(roomName);
    }

    public void sendMessage(String text) {
        if (currentRoom == null) return;
        chatUseCase.sendMessage(currentRoom, currentUserName, text);
    }

    public void sendFile(File file) {
        if (currentRoom == null || file == null) return;
        try {
            chatUseCase.sendFile(currentRoom, currentUserName, file);
        } catch (IOException e) {
            System.out.println("Ошибка отправки файла: " + e.getMessage());
        }
    }

    public void createRoom(String roomName) {
        chatUseCase.createRoom(roomName);
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }

    private void handleMessage(Message message) {
        Platform.runLater(() -> {
            switch (message.getType()) {
                case ROOM_LIST:
                    RoomListMessage roomList = (RoomListMessage) message;
                    rooms.setAll(roomList.getRooms());
                    break;
                case TEXT_MESSAGE:
                    messages.add(message);
                    break;
                case FILE_MESSAGE:
                    messages.add(message);
                    break;
                case USER_LIST:
                    UserListMessage userList = (UserListMessage) message;
                    users.setAll(userList.getUsers());
                    break;
                case USER_JOIN:
                case USER_LEAVE:
                    messages.add(message);
                    break;
                case HISTORY_MESSAGE:
                    HistoryMessage history = (HistoryMessage) message;
                    for (String raw : history.getRawMessages()) {
                        try {
                            Message m = org.gogil.chat.client.util.JsonUtil.parseMessage(raw);
                            messages.add(m);
                        } catch (Exception e) {
                            System.out.println("Ошибка парсинга истории: " + e.getMessage());
                        }
                    }
                    break;
                case ERROR:
                    errorText.set(message.getRoomName());
                    break;
            }
        });
    }
}
