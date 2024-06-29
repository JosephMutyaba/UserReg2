package org.pahappa.systems.registrationapp.dao;

import org.hibernate.HibernateException;
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


//    public boolean deleteDependantById(Long dependantId) {
//        Session session = null;
//        Transaction transaction = null;
//        try {
//            session = sessionFactory.openSession();
//            transaction = session.beginTransaction();
//            Dependant dependant = (Dependant) session.get(Dependant.class, dependantId);
//            if (dependant != null) {
//                dependant.setDeleted(true);
//                session.update(dependant);
//                transaction.commit();
//                return true;
//            }else {
//                return false;
//            }
//
//        }catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//            return false;
//        }
//    }

    public List<Dependant> getAllDependantsByUserId(Long userId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.createQuery("from Dependant where user_id = :userId and deleted = false")
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
            return session.createQuery("from Dependant where gender = :gender and user_id = :userId and deleted = false")
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
            return session.createQuery("from Dependant where username = :username and user_id = :userId and deleted = false")
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
            return session.createQuery("from Dependant where firstname = :firstname and user_id = :userId and deleted = false")
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
            return session.createQuery("from Dependant where lastname = :lastname and user_id = :userId and deleted = false")
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
            int updatedCount = session.createQuery("update Dependant set deleted = true where user_id = :userId")
                    .setParameter("userId", userId)
                    .executeUpdate();
            transaction.commit();
            return updatedCount > 0;
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
                dependant.setDeleted(true);
                session.update(dependant);
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

    public List<Dependant> getAllDependants() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.createQuery("from Dependant where deleted=false").list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public boolean deleteAllDependantsFromDb() {
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            int updatedCount = session.createQuery("UPDATE Dependant SET deleted = true").executeUpdate();
            transaction.commit();

            return updatedCount > 0; // Return true if any records were updated
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // Handle or log the exception as needed
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Dependant> findDependantsByUserAndGender(Long selectedUserId, String selectedGender) {
        Session session=null;
        try {
            session=sessionFactory.openSession();
            return session.createQuery("from Dependant where user_id = :userId and gender = :selGender and deleted=false")
                    .setParameter("selGender", selectedGender)
                    .setParameter("userId", selectedUserId)
                    .list();

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            return null;
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Dependant> findDependantsByUser(Long selectedUserId) {
        Session session=null;
        try {
            session=sessionFactory.openSession();
            return session.createQuery("from Dependant where user_id = :userId and deleted=false ")
                    .setParameter("userId", selectedUserId)
                    .list();

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            return null;
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Dependant> findDependantsByGender(String selectedGender) {
        Session session=null;
        try {
            session=sessionFactory.openSession();
            return session.createQuery("from Dependant where gender = :selGender and deleted=false ")
                    .setParameter("selGender", selectedGender)
                    .list();

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            return null;
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }
}