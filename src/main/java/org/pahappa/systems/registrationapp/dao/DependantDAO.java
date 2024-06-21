package org.pahappa.systems.registrationapp.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pahappa.systems.registrationapp.config.SessionConfiguration;
import org.pahappa.systems.registrationapp.models.Dependant;

import java.util.List;

public class DependantDAO {

    private final SessionFactory sessionFactory;

    public DependantDAO() {
        this.sessionFactory = SessionConfiguration.getSessionFactory();
    }

    public boolean addDependant(Dependant dependant) {
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(dependant);
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

    public List<Dependant> getAllDependantsByUserId(Long userId) {
        try {
            Session session = sessionFactory.openSession();
            return session.createQuery("from Dependant where user_id = :userId")
                    .setParameter("userId", userId)
                    .list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Dependant> searchDependantsByGenderAndUserId(String gender, Long userId) {
        try {
            Session session = sessionFactory.openSession();
            return session.createQuery("from Dependant where gender = :gender and userId = :userId")
                    .setParameter("gender", gender)
                    .setParameter("userId", userId)
                    .list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Dependant> searchDependantsByUsernameAndUserId(String username, Long userId) {
        try {
            Session session = sessionFactory.openSession();
            return session.createQuery("from Dependant where username = :username and user_id = :userId")
                    .setParameter("username", username)
                    .setParameter("userId", userId)
                    .list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Dependant> searchDependantsByFirstnameAndUserId(String firstname, Long userId) {
        try {
            Session session = sessionFactory.openSession();
            return session.createQuery("from Dependant where firstname = :firstname and user_id = :userId")
                    .setParameter("firstname", firstname)
                    .setParameter("userId", userId)
                    .list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Dependant> searchDependantsByLastnameAndUserId(String lastname, Long userId) {
        try {
            Session session = sessionFactory.openSession();
            return session.createQuery("from Dependant where lastname = :lastname and user_id = :userId")
                    .setParameter("lastname", lastname)
                    .setParameter("userId", userId)
                    .list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean deleteDependantsByUserId(Long userId) {
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            int deletedCount = session.createQuery("DELETE FROM Dependant where user_id = :userId")
                    .setParameter("userId", userId)
                    .executeUpdate();
            transaction.commit();
            return deletedCount > 0;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
            return false;
        }
    }
}








//package org.pahappa.systems.registrationapp.dao;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.pahappa.systems.registrationapp.config.SessionConfiguration;
//import org.pahappa.systems.registrationapp.models.Dependant;
//
//import java.util.List;
//
//public class DependantDAO {
//
//    private final SessionFactory sessionFactory;
//
//    public DependantDAO() {
//        this.sessionFactory = SessionConfiguration.getSessionFactory();
//    }
//
//    public boolean addDependant(Dependant dependant) {
//        Transaction transaction = null;
//        try {
//            Session session = sessionFactory.openSession();
//            transaction = session.beginTransaction();
//            session.save(dependant);
//            transaction.commit();
//            return true;
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            System.out.println(e.getMessage());
//            return false;
//        }
//    }
//
//    public List<Dependant> getAllDependants() {
//        try {
//            Session session = sessionFactory.openSession();
//            return session.createQuery("from Dependant").list();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return null;
//        }
//    }
//
//    public List<Dependant> searchDependantsByGender(String gender) {
//        try {
//            Session session = sessionFactory.openSession();
//            return session.createQuery("from Dependant where gender = :gender")
//                    .setParameter("gender", gender)
//                    .list();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return null;
//        }
//    }
//
//    public List<Dependant> searchDependantsByName(String name) {
//        try {
//            Session session = sessionFactory.openSession();
//            return session.createQuery("from Dependant where firstname = :name or lastname = :name or username = :name")
//                    .setParameter("name", name)
//                    .list();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return null;
//        }
//    }
//
//    public boolean deleteDependant(Long id) {
//        Transaction transaction = null;
//        try {
//            Session session = sessionFactory.openSession();
//            transaction = session.beginTransaction();
//            Dependant dependant = (Dependant) session.get(Dependant.class, id);
//            if (dependant != null) {
//                session.delete(dependant);
//                transaction.commit();
//                return true;
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            System.out.println(e.getMessage());
//            return false;
//        }
//    }
//
//    public void deleteAllDependants() {
//        Transaction transaction = null;
//        try {
//            Session session = sessionFactory.openSession();
//            transaction = session.beginTransaction();
//            session.createQuery("DELETE FROM Dependant").executeUpdate();
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            System.out.println(e.getMessage());
//        }
//    }
//
//    // New methods added to support UserView functionalities
//
//    public List<Dependant> searchDependantsByGenderAndUserId(String gender, Long userId) {
//        try {
//            Session session = sessionFactory.openSession();
//            return session.createQuery("from Dependant where gender = :gender and userId = :userId")
//                    .setParameter("gender", gender)
//                    .setParameter("userId", userId)
//                    .list();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return null;
//        }
//    }
//
//    public List<Dependant> searchDependantsByUsernameAndUserId(String username, Long userId) {
//        try {
//            Session session = sessionFactory.openSession();
//            return session.createQuery("from Dependant where username = :username and userId = :userId")
//                    .setParameter("username", username)
//                    .setParameter("userId", userId)
//                    .list();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return null;
//        }
//    }
//
//    public List<Dependant> searchDependantsByFirstnameAndUserId(String firstname, Long userId) {
//        try {
//            Session session = sessionFactory.openSession();
//            return session.createQuery("from Dependant where firstname = :firstname and userId = :userId")
//                    .setParameter("firstname", firstname)
//                    .setParameter("userId", userId)
//                    .list();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return null;
//        }
//    }
//
//    public List<Dependant> searchDependantsByLastnameAndUserId(String lastname, Long userId) {
//        try {
//            Session session = sessionFactory.openSession();
//            return session.createQuery("from Dependant where lastname = :lastname and userId = :userId")
//                    .setParameter("lastname", lastname)
//                    .setParameter("userId", userId)
//                    .list();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return null;
//        }
//    }
//
//}
