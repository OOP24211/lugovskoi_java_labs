package org.gogil.chat.client.websocket;

import org.gogil.chat.client.util.JsonUtil;
import org.gogil.chat.model.Message;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.function.Consumer;

public class ChatWebSocketClient extends WebSocketClient {

    private Consumer<Message> onMessageReceived;
    private Runnable onConnected;
    private Runnable onDisconnected;

    public ChatWebSocketClient(String serverUrl) throws Exception {
        super(new URI(serverUrl));
    }

    public void setOnMessageReceived(Consumer<Message> onMessageReceived) {
        this.onMessageReceived = onMessageReceived;
    }

    public void setOnConnected(Runnable onConnected) {
        this.onConnected = onConnected;
    }

    public void setOnDisconnected(Runnable onDisconnected) {
        this.onDisconnected = onDisconnected;
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        if (onConnected != null) {
            onConnected.run();
        }
    }

    @Override
    public void onMessage(String json) {
        if (onMessageReceived == null) return;
        try {
            Message message = JsonUtil.parseMessage(json);
            onMessageReceived.accept(message);
        } catch (Exception e) {
            System.out.println("Ошибка парсинга: " + e.getMessage());
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        if (onDisconnected != null) {
            onDisconnected.run();
        }
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("Ошибка WebSocket: " + ex.getMessage());
    }

    public void sendMessage(Message message) {
        String json = JsonUtil.toJson(message);
        send(json);
    }
}
