package org.gogil.chat.client.usecase;

import org.gogil.chat.client.websocket.ChatWebSocketClient;
import org.gogil.chat.model.AuthMessage;
import org.gogil.chat.model.MessageType;

public class LoginUseCase {

    private final ChatWebSocketClient client;

    public LoginUseCase(ChatWebSocketClient client) {
        this.client = client;
    }

    public void login(String userName, String password) {
        AuthMessage message = new AuthMessage(MessageType.LOGIN, userName, password);
        client.sendMessage(message);
    }

    public void register(String userName, String password) {
        AuthMessage message = new AuthMessage(MessageType.REGISTER, userName, password);
        client.sendMessage(message);
    }
}
