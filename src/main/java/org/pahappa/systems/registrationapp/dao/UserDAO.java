package org.pahappa.systems.registrationapp.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pahappa.systems.registrationapp.config.SessionConfiguration;
import org.pahappa.systems.registrationapp.models.User;

import java.util.List;

public class UserDAO {

    private final SessionFactory sessionFactory;

    public UserDAO() {
        this.sessionFactory = SessionConfiguration.getSessionFactory();
    }

    public boolean registerUser(User user) {
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();
            // start a transaction
            transaction = session.beginTransaction();
            // save the user object
            session.save(user);
            // commit transaction
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<User> displayAllUsers() {
        try {
            Session session = sessionFactory.openSession();
            return session.createQuery("from User").list();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public User getUserOfUsername(String username) {
        Transaction transaction = null;
        User user = null;
        try {
            Session session = sessionFactory.openSession();
            transaction =session.beginTransaction();

            user = (User) session.createQuery("from User where username = :username")
                    .setParameter("username", username)
                    .uniqueResult();

            transaction.commit();
        }catch (Exception ex){
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return user;
    }

    public boolean updateUserOfUsername(User user) {
        Transaction transaction = null;
        try  {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteUserOfUsername(String username) {
        Transaction transaction = null;
        try  {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            User user = (User) session.createQuery("from User where username = :username")
                    .setParameter("username", username)
                    .uniqueResult();
            if (user != null) {
                session.delete(user);
                transaction.commit();
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public void deleteAllUsers() {
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

}
