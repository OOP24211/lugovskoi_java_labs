package org.gogil.chat.server.auth;

public interface IUserRepository {
    boolean register(String userName, String password);
    boolean login(String userName, String password);
    boolean userExists(String userName);
}
