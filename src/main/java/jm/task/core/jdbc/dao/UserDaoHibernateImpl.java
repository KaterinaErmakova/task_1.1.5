package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "lastName VARCHAR(100) NOT NULL, " +
                    "age TINYINT NOT NULL)").executeUpdate();
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        User newUser = new User(name, lastName, age);
        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(newUser);
            transaction.commit();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<User> query = session.createQuery("FROM User", User.class);
            users = query.getResultList();
            transaction.commit();
            return users;
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
