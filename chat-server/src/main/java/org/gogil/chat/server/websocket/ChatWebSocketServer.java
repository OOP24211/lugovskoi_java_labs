package org.gogil.chat.server.websocket;

import org.gogil.chat.model.*;
import org.gogil.chat.server.auth.IUserRepository;
import org.gogil.chat.server.processor.*;
import org.gogil.chat.server.room.IRoomManager;
import org.gogil.chat.server.room.RoomManagerImplementation;
import org.gogil.chat.server.util.JsonUtil;
import org.gogil.chat.server.db.RoomRepository;
import org.gogil.chat.server.entity.RoomEntity;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.gogil.chat.server.auth.PostgresUserRepository;

public class ChatWebSocketServer extends WebSocketServer {
    private final Map<WebSocket, User> connectedUsers = new ConcurrentHashMap<>();
    private final IRoomManager roomManager = new RoomManagerImplementation();
    private final MessageDispatcher dispatcher = new MessageDispatcher();
    private final IUserRepository userRepository = new PostgresUserRepository();

    public ChatWebSocketServer(int port) {
        super(new InetSocketAddress(port));
        registerProcessors();
        createDefaultRooms();
    }

    private void registerProcessors() {
        dispatcher.register(new TextMessageProcessor(roomManager, connectedUsers));
        dispatcher.register(new FileMessageProcessor(roomManager, connectedUsers));
        dispatcher.register(new JoinRoomProcessor(roomManager, connectedUsers));
        dispatcher.register(new LeaveRoomProcessor(roomManager, connectedUsers));
        dispatcher.register(new CreateRoomProcessor(roomManager, connectedUsers));
        dispatcher.register(new LoginProcessor(userRepository, connectedUsers));
        dispatcher.register(new RegisterProcessor(userRepository, connectedUsers));
        dispatcher.register(new RoomListProcessor(roomManager, connectedUsers));
    }

    private void createDefaultRooms() {
        createRoomIfNotExists("general", "server");

        RoomRepository roomRepository = new RoomRepository();
        roomRepository.getAllRooms().forEach(entity -> {
            roomManager.createRoom(entity.getName(), entity.getCreatedBy());
        });
    }

    private void createRoomIfNotExists(String name, String createdBy) {
        RoomRepository roomRepository = new RoomRepository();
        if (!roomRepository.exists(name)) {
            roomRepository.save(new RoomEntity(name, createdBy));
        }
        roomManager.createRoom(name, createdBy);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        User user = new User();
        connectedUsers.put(conn, user);

        List<String> roomNames = roomManager.getAllRooms().stream()
                .map(Room::getName)
                .collect(Collectors.toList());
        RoomListMessage roomList = new RoomListMessage(roomNames);
        conn.send(JsonUtil.toJson(roomList));

        System.out.println("Подключился новый пользователь " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String json) {
        User sender = connectedUsers.get(conn);
        if (sender == null) return;

        System.out.println("Получено от " + sender.getUserName() + ": " + json);

        try {
            Message message = JsonUtil.parseMessage(json);
            dispatcher.dispatch(message, sender);
        }
        catch (Exception e) {
            System.out.println("Ошибка при парсинге " + e.getMessage());
        }
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        User user = connectedUsers.get(conn);
        if (user == null) return;

        System.out.println("Отключился: " + user.getUserName() + " комната: " + user.getCurrentRoom());

        if (user.getCurrentRoom() != null) {
            Message leaveMessage = new Message(MessageType.LEAVE_ROOM, user.getCurrentRoom(), user.getUserName());
            dispatcher.dispatch(leaveMessage, user);
        }

        connectedUsers.remove(conn);
        System.out.println("Пользователь отключился " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println("Ошибка " + ex.getMessage());
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("Сервер успешно запущен");
    }
}
