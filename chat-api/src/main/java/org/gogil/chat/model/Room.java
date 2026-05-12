package org.gogil.chat.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Collections;

public class Room {
    private String name;
    private String createdBy;
    private long createdAt;
    private final List<User> users = new CopyOnWriteArrayList<>();
    private final List<Message> history = new CopyOnWriteArrayList<>();

    private static final int MAX_HISTORY_SIZE = 100;

    public Room() {
        this.createdAt = System.currentTimeMillis();
    }

    public Room(String name, String createdBy) {
        this.name = name;
        this.createdBy = createdBy;
        this.createdAt = System.currentTimeMillis();
    }

    public void addUser(User user) {
        if (!hasUser(user.getUserName())) {
            users.add(user);
        }
    }

    public void removeUser(String userName) {
        users.removeIf(u -> u.getUserName().equals(userName));
    }

    public boolean hasUser(String userName) {
        return users.stream()
                .anyMatch(u -> u.getUserName().equals(userName));
    }

    public void addToHistory(Message message) {
        if (history.size() >= MAX_HISTORY_SIZE) {
            history.remove(0);
        }
        history.add(message);
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    public List<Message> getHistory() {
        return Collections.unmodifiableList(history);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
