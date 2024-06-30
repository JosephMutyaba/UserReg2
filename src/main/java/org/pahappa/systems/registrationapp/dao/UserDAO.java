package org.pahappa.systems.registrationapp.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pahappa.systems.registrationapp.config.SessionConfiguration;
import org.pahappa.systems.registrationapp.models.Dependant;
import org.pahappa.systems.registrationapp.models.User;

import java.util.Date;
import java.util.List;

public class UserDAO {

    private final SessionFactory sessionFactory;

    public UserDAO() {
        this.sessionFactory = SessionConfiguration.getSessionFactory();
    }

    public boolean registerUser(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error registering user: " + e.getMessage());
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<User> displayAllUsers() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.createQuery("from User where deleted = false").list();
        } catch (Exception e) {
            System.out.println("Error fetching all users: " + e.getMessage());
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public User getUserOfUsername(String username) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return (User) session.createQuery("from User where username = :username")
                    .setParameter("username", username)
                    .uniqueResult();
        } catch (Exception e) {
            System.out.println("Error fetching user by username: " + e.getMessage());
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public boolean updateUserOfUsername(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error updating user: " + e.getMessage());
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    public boolean deleteUserOfUsername(String username) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            User user = (User) session.createQuery("from User where username = :username")
                    .setParameter("username", username)
                    .uniqueResult();
            if (user != null) {
                user.setDeleted(true);
                session.update(user);

                // Soft delete dependants
                session.createQuery("update Dependant set deleted = true where user_id = :userId")
                        .setParameter("userId", user.getId())
                        .executeUpdate();

                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error deleting user: " + e.getMessage());
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public boolean deleteAllUsers() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            // Soft delete all users
            session.createQuery("update User set deleted = true").executeUpdate();

            // Soft delete all dependants
            session.createQuery("update Dependant set deleted = true").executeUpdate();

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error deleting all users: " + e.getMessage());
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<User> getUsersRegisteredOnADay(Date dateRegistered) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.createQuery("from User where dateOfRegistration=:date_of_registration")
                    .setParameter("date_of_registration", dateRegistered)
                    .list();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }finally {
            if(session != null) {
                session.close();
            }
        }
        return null;
    }
}

