package org.gogil.chat.server.processor;

import org.gogil.chat.model.Message;
import org.gogil.chat.model.MessageType;
import org.gogil.chat.model.User;

public interface IMessageProcessor {
    void process(Message message, User sender);
    MessageType getMessageType();
}
