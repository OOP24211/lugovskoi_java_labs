package org.gogil.chat.server.processor;

import java.util.HashMap;
import org.gogil.chat.model.Message;
import org.gogil.chat.model.MessageType;
import org.gogil.chat.model.User;
import java.util.Map;

public class MessageDispatcher {

    private final Map<MessageType, IMessageProcessor> processors = new HashMap<>();

    public void register(IMessageProcessor processor) {
        processors.put(processor.getMessageType(), processor);
    }

    public void dispatch(Message message, User sender) {
        if (message == null) return;

        IMessageProcessor processor = processors.get(message.getType());

        if (processor == null) {
            System.out.println("Данный тип не поддерживается " + message.getType());
            return;
        }

        processor.process(message, sender);
    }
}
