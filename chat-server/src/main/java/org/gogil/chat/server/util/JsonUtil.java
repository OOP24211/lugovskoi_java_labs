package org.gogil.chat.server.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.gogil.chat.model.*;

public class JsonUtil {
    private static final Gson gson = new GsonBuilder().create();

    private JsonUtil() {}

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static Message parseMessage(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        if (!jsonObject.has("type")) {
            throw new IllegalArgumentException("Нет поля type: " + json);
        }

        String typeStr = jsonObject.get("type").getAsString();
        MessageType type = MessageType.valueOf(typeStr);

        switch (type) {
            case TEXT_MESSAGE:
                return gson.fromJson(json, TextMessage.class);
            case FILE_MESSAGE:
                return gson.fromJson(json, FileMessage.class);
            case LOGIN:
            case REGISTER:
                return gson.fromJson(json, AuthMessage.class);
            default:
                return gson.fromJson(json, Message.class);
        }
    }
}
