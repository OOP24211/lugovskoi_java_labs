package org.gogil.chat.client.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.gogil.chat.client.viewmodel.ChatViewModel;
import org.gogil.chat.model.Message;

import java.io.File;
import javafx.application.Platform;

public class ChatController {

    @FXML
    private ListView<Message> messageListView;

    @FXML
    private ListView<String> roomListView;

    @FXML
    private ListView<String> userListView;

    @FXML
    private TextField messageField;

    @FXML
    private Label currentRoomLabel;

    private ChatViewModel viewModel;

    public void setViewModel(ChatViewModel viewModel) {
        this.viewModel = viewModel;
        bindToViewModel();
    }

    private void bindToViewModel() {
        roomListView.setItems(viewModel.rooms);
        messageListView.setItems(viewModel.messages);
        userListView.setItems(viewModel.users);

        messageListView.setCellFactory(list -> new MessageCell(
                (Stage) messageListView.getScene().getWindow()
        ));

        roomListView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldRoom, newRoom) -> {
                    if (newRoom != null && !newRoom.equals(oldRoom)) {
                        currentRoomLabel.setText(newRoom);
                        viewModel.joinRoom(newRoom);
                    }
                }
        );

        messageField.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
                onSendClicked();
            }
        });

        viewModel.messages.addListener((javafx.collections.ListChangeListener<Message>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    Platform.runLater(() -> messageListView.scrollTo(viewModel.messages.size() - 1));
                }
            }
        });
    }

    @FXML
    private void onSendClicked() {
        String text = messageField.getText().trim();
        if (text.isEmpty()) return;
        viewModel.sendMessage(text);
        messageField.clear();
    }

    @FXML
    private void onSendFileClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл");
        Stage stage = (Stage) messageField.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            viewModel.sendFile(file);
        }
    }

    @FXML
    private void onCreateRoomClicked() {
        Stage dialog = new Stage();
        dialog.setTitle("Создать комнату");
        dialog.initModality(javafx.stage.Modality.APPLICATION_MODAL);

        javafx.scene.layout.VBox vbox = new javafx.scene.layout.VBox(12);
        vbox.setStyle("-fx-padding: 20; -fx-background-color: #1e1e2e;");
        vbox.setAlignment(javafx.geometry.Pos.CENTER);

        Label label = new Label("Название комнаты:");
        label.setStyle("-fx-text-fill: #ccccdd; -fx-font-size: 13;");

        TextField field = new TextField();
        field.setPromptText("Введите название...");
        field.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: #ff6b6b; -fx-font-size: 12;");

        Button createBtn = new Button("Создать");
        createBtn.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        createBtn.setMaxWidth(Double.MAX_VALUE);

        javafx.beans.value.ChangeListener<String> errorListener = (obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.isEmpty()) {
                errorLabel.setText(newVal);
                viewModel.errorText.set("");
            }
        };
        viewModel.errorText.addListener(errorListener);

        dialog.setOnHidden(e -> viewModel.errorText.removeListener(errorListener));

        createBtn.setOnAction(e -> {
            String name = field.getText().trim();
            viewModel.createRoom(name);
        });

        viewModel.rooms.addListener((javafx.collections.ListChangeListener<String>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    dialog.close();
                }
            }
        });

        vbox.getChildren().addAll(label, field, errorLabel, createBtn);

        javafx.scene.Scene scene = new javafx.scene.Scene(vbox, 300, 170);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        dialog.setScene(scene);
        dialog.showAndWait();
    }
}
