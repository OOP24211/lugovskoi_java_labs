package org.gogil.chat.server.db;

import org.gogil.chat.server.entity.MessageEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class MessageRepository {

    public void save(MessageEntity message) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(message);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Ошибка сохранения сообщения: " + e.getMessage());
        }
    }

    public List<MessageEntity> getHistory(String roomName, int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM MessageEntity WHERE roomName = :roomName ORDER BY sentAt ASC", MessageEntity.class)
                    .setParameter("roomName", roomName)
                    .setMaxResults(limit)
                    .list();
        } catch (Exception e) {
            System.out.println("Ошибка загрузки истории: " + e.getMessage());
            return List.of();
        }
    }
}
