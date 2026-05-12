package org.gogil.chat.client.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.gogil.chat.model.FileMessage;
import org.gogil.chat.model.Message;
import org.gogil.chat.model.MessageType;
import org.gogil.chat.model.TextMessage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;

public class MessageCell extends ListCell<Message> {

    private final VBox container = new VBox(4);
    private final Label senderLabel = new Label();
    private final Label textLabel = new Label();
    private final ImageView imageView = new ImageView();
    private final Label fileLabel = new Label();
    private final Stage stage;

    public MessageCell(Stage stage) {
        this.stage = stage;
        senderLabel.getStyleClass().add("message-sender");
        textLabel.setWrapText(true);
        textLabel.getStyleClass().add("message-text");
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
        fileLabel.getStyleClass().add("file-label");
        container.setPadding(new Insets(4));
    }

    @Override
    protected void updateItem(Message message, boolean empty) {
        super.updateItem(message, empty);

        if (empty || message == null) {
            setGraphic(null);
            setText(null);
            return;
        }

        container.getChildren().clear();
        textLabel.getStyleClass().removeAll("system-join", "system-leave");

        if (message.getType() == MessageType.TEXT_MESSAGE) {
            TextMessage tm = (TextMessage) message;
            senderLabel.setText(tm.getUserName());
            textLabel.setText(tm.getText());
            container.getChildren().addAll(senderLabel, textLabel);

        } else if (message.getType() == MessageType.FILE_MESSAGE) {
            FileMessage fm = (FileMessage) message;
            senderLabel.setText(fm.getUserName());
            container.getChildren().add(senderLabel);

            if (fm.isImage()) {
                byte[] imageBytes = Base64.getDecoder().decode(fm.getFileContent());
                Image image = new Image(new ByteArrayInputStream(imageBytes));
                imageView.setImage(image);
                imageView.setOnMouseClicked(e -> saveFile(fm));
                container.getChildren().add(imageView);
            } else {
                fileLabel.setText("📎 " + fm.getFileName());
                fileLabel.setOnMouseClicked(e -> saveFile(fm));
                container.getChildren().add(fileLabel);
            }

        } else if (message.getType() == MessageType.USER_JOIN) {
            textLabel.setText(message.getUserName() + " вошёл в комнату");
            textLabel.getStyleClass().add("system-join");
            container.getChildren().add(textLabel);

        } else if (message.getType() == MessageType.USER_LEAVE) {
            textLabel.setText(message.getUserName() + " покинул комнату");
            textLabel.getStyleClass().add("system-leave");
            container.getChildren().add(textLabel);
        }

        setGraphic(container);
        setText(null);
    }

    private void saveFile(FileMessage fm) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить файл");
        fileChooser.setInitialFileName(fm.getFileName());
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try {
                byte[] bytes = Base64.getDecoder().decode(fm.getFileContent());
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(bytes);
                }
            } catch (Exception e) {
                System.out.println("Ошибка сохранения файла: " + e.getMessage());
            }
        }
    }
}
