package org.gogil.chat.server.auth;

import org.mindrot.jbcrypt.BCrypt;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class InMemoryUserRepository implements IUserRepository {
    private final Map<String, String> users = new ConcurrentHashMap<>();

    @Override
    public boolean register(String userName, String password) {
        if (userExists(userName)) {
            return false;
        }
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        users.put(userName, hashedPassword);
        return true;
    }

    @Override
    public boolean login(String userName, String password) {
        String hashedPassword = users.get(userName);
        if (hashedPassword == null) {
            return false;
        }
        return BCrypt.checkpw(password, hashedPassword);
    }

    @Override
    public boolean userExists(String userName) {
        return users.containsKey(userName);
    }
}
