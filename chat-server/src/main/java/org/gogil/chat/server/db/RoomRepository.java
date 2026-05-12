package org.gogil.chat.server.db;

import org.gogil.chat.server.entity.RoomEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class RoomRepository {

    public boolean save(RoomEntity room) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(room);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Ошибка сохранения комнаты: " + e.getMessage());
            return false;
        }
    }

    public RoomEntity findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM RoomEntity WHERE name = :name", RoomEntity.class)
                    .setParameter("name", name)
                    .uniqueResult();
        } catch (Exception e) {
            System.out.println("Ошибка поиска комнаты: " + e.getMessage());
            return null;
        }
    }

    public List<RoomEntity> getAllRooms() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM RoomEntity", RoomEntity.class).list();
        } catch (Exception e) {
            System.out.println("Ошибка загрузки комнат: " + e.getMessage());
            return List.of();
        }
    }

    public boolean exists(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(r) FROM RoomEntity r WHERE r.name = :name", Long.class)
                    .setParameter("name", name)
                    .uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            System.out.println("Ошибка проверки комнаты: " + e.getMessage());
            return false;
        }
    }
}
