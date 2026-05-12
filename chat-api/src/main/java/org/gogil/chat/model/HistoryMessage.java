package org.gogil.chat.model;

import java.util.List;

public class HistoryMessage extends Message {
    private List<String> rawMessages;

    public HistoryMessage() {
        super(MessageType.HISTORY_MESSAGE);
    }

    public HistoryMessage(String roomName, List<String> rawMessages) {
        super(MessageType.HISTORY_MESSAGE);
        setRoomName(roomName);
        this.rawMessages = rawMessages;
    }

    public List<String> getRawMessages() {
        return rawMessages;
    }

    public void setRawMessages(List<String> rawMessages) {
        this.rawMessages = rawMessages;
    }
}
