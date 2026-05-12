package org.gogil.chat.model;

public class TextMessage extends Message {
    private String text;

    public TextMessage() {
        super(MessageType.TEXT_MESSAGE);
    }

    public TextMessage(String roomName, String userName, String text) {
        super(MessageType.TEXT_MESSAGE, roomName, userName);
        this.text = text;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
