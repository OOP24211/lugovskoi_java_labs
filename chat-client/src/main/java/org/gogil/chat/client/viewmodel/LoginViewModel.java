package org.gogil.chat.client.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.gogil.chat.client.usecase.LoginUseCase;
import org.gogil.chat.client.websocket.ChatWebSocketClient;
import org.gogil.chat.model.AuthMessage;
import org.gogil.chat.model.Message;
import org.gogil.chat.model.MessageType;

public class LoginViewModel {

    private final LoginUseCase loginUseCase;
    private final ChatWebSocketClient client;

    public final StringProperty userName = new SimpleStringProperty("");
    public final StringProperty password = new SimpleStringProperty("");
    public final StringProperty errorText = new SimpleStringProperty("");
    public final BooleanProperty authSuccess = new SimpleBooleanProperty(false);

    private Runnable onLoginSuccess;
    private Runnable onRegisterSuccess;
    private boolean isRegistering = false;

    public LoginViewModel(ChatWebSocketClient client) {
        this.client = client;
        this.loginUseCase = new LoginUseCase(client);
        this.client.setOnMessageReceived(this::handleMessage);
    }

    public void setOnLoginSuccess(Runnable onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
    }

    public void setOnRegisterSuccess(Runnable onRegisterSuccess) {
        this.onRegisterSuccess = onRegisterSuccess;
    }

    public void login() {
        String name = userName.get().trim();
        String pass = password.get().trim();
        isRegistering = false;
        loginUseCase.login(name, pass);
    }

    public void register() {
        String name = userName.get().trim();
        String pass = password.get().trim();
        isRegistering = true;
        loginUseCase.register(name, pass);
    }

    private void handleMessage(Message message) {
        Platform.runLater(() -> {
            if (message.getType() == MessageType.AUTH_SUCCESS) {
                authSuccess.set(true);
                errorText.set("");
                if (isRegistering && onRegisterSuccess != null) {
                    onRegisterSuccess.run();
                } else if (!isRegistering && onLoginSuccess != null) {
                    onLoginSuccess.run();
                }
            } else if (message.getType() == MessageType.AUTH_ERROR) {
                AuthMessage authMessage = (AuthMessage) message;
                errorText.set(authMessage.getErrorText());
            }
        });
    }

    public ChatWebSocketClient getClient() {
        return client;
    }

    public String getUserName() {
        return userName.get();
    }
}
