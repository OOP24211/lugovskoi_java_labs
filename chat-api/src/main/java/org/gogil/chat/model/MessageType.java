package org.gogil.chat.model;

public enum MessageType {
    JOIN_ROOM,
    LEAVE_ROOM,
    TEXT_MESSAGE,
    FILE_MESSAGE,
    CREATE_ROOM,

    USER_JOIN,
    USER_LEAVE,
    USER_LIST,
    HISTORY_MESSAGE,
    ROOM_LIST,
    ERROR,

    LOGIN,
    REGISTER,
    AUTH_SUCCESS,
    AUTH_ERROR
}
