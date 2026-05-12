package org.gogil.chat.server.websocket;

import org.gogil.chat.server.db.HibernateUtil;

public class Server {
    public static void main(String[] args) {
        System.out.println("Инициализация базы данных");
        HibernateUtil.getSessionFactory();
        System.out.println("База данных инициализирована");

        String portEnv = System.getenv("SERVER_PORT");
        int port = portEnv != null ? Integer.parseInt(portEnv) : 8080;

        ChatWebSocketServer server = new ChatWebSocketServer(port);
        server.start();
        System.out.println("Сервер запущен и работает на порту: " + port);
    }
}
