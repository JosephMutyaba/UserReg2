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
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(dependant);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Dependant> getAllDependantsByUserId(Long userId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.createQuery("from Dependant where user_id = :userId")
                    .setParameter("userId", userId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Dependant> searchDependantsByGenderAndUserId(String gender, Long userId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.createQuery("from Dependant where gender = :gender and user_id = :userId")
                    .setParameter("gender", gender)
                    .setParameter("userId", userId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Dependant> searchDependantsByUsernameAndUserId(String username, Long userId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.createQuery("from Dependant where username = :username and user_id = :userId")
                    .setParameter("username", username)
                    .setParameter("userId", userId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Dependant> searchDependantsByFirstnameAndUserId(String firstname, Long userId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.createQuery("from Dependant where firstname = :firstname and user_id = :userId")
                    .setParameter("firstname", firstname)
                    .setParameter("userId", userId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Dependant> searchDependantsByLastnameAndUserId(String lastname, Long userId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.createQuery("from Dependant where lastname = :lastname and user_id = :userId")
                    .setParameter("lastname", lastname)
                    .setParameter("userId", userId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public boolean deleteDependantsByUserId(Long userId) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
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
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public boolean deleteDependantById(Long id) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Dependant dependant = (Dependant) session.get(Dependant.class, id);
            if (dependant != null) {
                session.delete(dependant);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public boolean updateDependant(Dependant dependant) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(dependant);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Dependant getDependantById(Long id) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return (Dependant) session.get(Dependant.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
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
//    public List<Dependant> getAllDependantsByUserId(Long userId) {
//        try {
//            Session session = sessionFactory.openSession();
//            return session.createQuery("from Dependant where user_iiid = :userId")
//                    .setParameter("userId", userId)
//                    .list();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return null;
//        }
//    }
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
//            return session.createQuery("from Dependant where username = :username and user_id = :userId")
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
//            return session.createQuery("from Dependant where firstname = :firstname and user_id = :userId")
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
//            return session.createQuery("from Dependant where lastname = :lastname and user_id = :userId")
//                    .setParameter("lastname", lastname)
//                    .setParameter("userId", userId)
//                    .list();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return null;
//        }
//    }
//
//    public boolean deleteDependantsByUserId(Long userId) {
//        Transaction transaction = null;
//        try {
//            Session session = sessionFactory.openSession();
//            transaction = session.beginTransaction();
//            int deletedCount = session.createQuery("DELETE FROM Dependant where user_iiid = :userId")
//                    .setParameter("userId", userId)
//                    .executeUpdate();
//            transaction.commit();
//            return deletedCount > 0;
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            System.out.println(e.getMessage());
//            return false;
//        }
//    }
//
//    public boolean deleteDependantById(Long id) {
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
//                transaction.rollback();
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
//    public boolean updateDependant(Dependant dependant) {
//        Transaction transaction = null;
//        try {
//            Session session = sessionFactory.openSession();
//            transaction = session.beginTransaction();
//            session.saveOrUpdate(dependant);
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
//    public Dependant getDependantById(Long id) {
//        try {
//            Session session = sessionFactory.openSession();
//            return (Dependant) session.get(Dependant.class, id);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return null;
//        }
//    }
//}
//
