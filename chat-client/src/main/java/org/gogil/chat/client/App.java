package org.gogil.chat.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.gogil.chat.client.ui.ChatController;
import org.gogil.chat.client.ui.LoginController;
import org.gogil.chat.client.viewmodel.ChatViewModel;
import org.gogil.chat.client.viewmodel.LoginViewModel;
import org.gogil.chat.client.websocket.ChatWebSocketClient;

public class App extends Application {

    private ChatWebSocketClient client;

    @Override
    public void start(Stage stage) throws Exception {
        String serverUrl = System.getenv("SERVER_URL");
        if (serverUrl == null) serverUrl = "ws://localhost:8080";
        client = new ChatWebSocketClient(serverUrl);
        client.connect();
        showLoginScreen(stage);
    }

    private void showLoginScreen(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        LoginController controller = loader.getController();
        LoginViewModel viewModel = new LoginViewModel(client);

        viewModel.setOnLoginSuccess(() -> {
            try {
                showChatScreen(stage, viewModel.getUserName());
            } catch (Exception e) {
                System.out.println("Ошибка перехода в чат: " + e.getMessage());
            }
        });

        viewModel.setOnRegisterSuccess(() -> {
            try {
                showLoginScreen(stage);
            } catch (Exception e) {
                System.out.println("Ошибка перехода на вход: " + e.getMessage());
            }
        });

        controller.setViewModel(viewModel);
        stage.setTitle("Чат");
        stage.setScene(scene);
        stage.show();
    }

    private void showChatScreen(Stage stage, String userName) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/chat.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        ChatController controller = loader.getController();
        ChatViewModel viewModel = new ChatViewModel(client, userName);
        controller.setViewModel(viewModel);

        stage.setTitle("Чат — " + userName);
        stage.setScene(scene);
    }

    @Override
    public void stop() throws Exception {
        if (client != null && client.isOpen()) {
            client.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
