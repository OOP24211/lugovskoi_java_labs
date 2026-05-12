package org.gogil.chat.server.processor;

import org.gogil.chat.model.*;
import org.gogil.chat.server.auth.IUserRepository;
import org.gogil.chat.server.util.JsonUtil;
import org.gogil.chat.server.validator.AuthValidator;
import org.gogil.chat.server.validator.ValidationException;
import org.java_websocket.WebSocket;
import java.util.Map;

public class RegisterProcessor implements IMessageProcessor {
    private final IUserRepository userRepository;
    private final Map<WebSocket, User> connectedUsers;
    private final AuthValidator authValidator = new AuthValidator();

    public RegisterProcessor(IUserRepository userRepository, Map<WebSocket, User> connectedUsers) {
        this.userRepository = userRepository;
        this.connectedUsers = connectedUsers;
    }

    @Override
    public void process(Message message, User sender) {
        AuthMessage authMessage = (AuthMessage) message;
        String userName = authMessage.getUserName();
        String password = authMessage.getPassword();

        try {
            authValidator.validate(userName, password);
        } catch (ValidationException e) {
            sendError(sender, e.getMessage());
            return;
        }

        boolean registered = userRepository.register(userName, password);

        if (!registered) {
            sendError(sender, "Пользователь с таким именем уже существует");
            return;
        }

        sender.setUserName(userName);
        sendSuccess(sender);
    }

    private void sendSuccess(User user) {
        AuthMessage response = new AuthMessage();
        response.setType(MessageType.AUTH_SUCCESS);
        response.setSuccess(true);
        sendToUser(user, JsonUtil.toJson(response));
    }

    private void sendError(User user, String errorText) {
        AuthMessage response = new AuthMessage();
        response.setType(MessageType.AUTH_ERROR);
        response.setSuccess(false);
        response.setErrorText(errorText);
        sendToUser(user, JsonUtil.toJson(response));
    }

    private void sendToUser(User user, String json) {
        connectedUsers.entrySet().stream()
                .filter(entry -> entry.getValue() == user)
                .map(Map.Entry::getKey)
                .findFirst()
                .ifPresent(ws -> ws.send(json));
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.REGISTER;
    }
}
