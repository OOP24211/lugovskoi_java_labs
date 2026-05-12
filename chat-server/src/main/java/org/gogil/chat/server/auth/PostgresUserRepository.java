package org.gogil.chat.server.auth;

import org.gogil.chat.server.db.HibernateUtil;
import org.gogil.chat.server.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

public class PostgresUserRepository implements IUserRepository {

    @Override
    public boolean register(String userName, String password) {
        if (userExists(userName)) {
            return false;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        UserEntity user = new UserEntity(userName, hashedPassword);

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Ошибка регистрации: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean login(String userName, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            UserEntity user = session.createQuery(
                            "FROM UserEntity WHERE username = :username", UserEntity.class)
                    .setParameter("username", userName)
                    .uniqueResult();

            if (user == null) return false;
            return BCrypt.checkpw(password, user.getPasswordHash());
        } catch (Exception e) {
            System.out.println("Ошибка входа: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean userExists(String userName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(u) FROM UserEntity u WHERE u.username = :username", Long.class)
                    .setParameter("username", userName)
                    .uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            System.out.println("Ошибка проверки пользователя: " + e.getMessage());
            return false;
        }
    }
}
