package org.pahappa.systems.registrationapp.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pahappa.systems.registrationapp.config.SessionConfiguration;
import org.pahappa.systems.registrationapp.models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminInitializer {
    private static final String ADMIN_USERNAME = "admina";
    private static final String ADMIN_PASSWORD = "admin@jsf"; // Replace with a secure password

    private static final SessionFactory sessionFactory;

    static {
        sessionFactory = SessionConfiguration.getSessionFactory();
    }

    public static void initializeAdmin() {
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            User admin = (User) session.createQuery("from User where username = :username")
                    .setParameter("username", ADMIN_USERNAME)
                    .uniqueResult();

            if (admin == null) {
                admin = new User();
                admin.setUsername(ADMIN_USERNAME);
                admin.setPassword(BCrypt.hashpw(ADMIN_PASSWORD, BCrypt.gensalt()));
                admin.setFirstname("Joseph");
                admin.setLastname("Maximillian");
                admin.setRole("ADMIN"); // Assuming you have a role field
                admin.setEmail("admin@email.com");

                String date = "03/04/2022";
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date dob = sdf.parse(date);

                admin.setDateOfBirth(dob);
                // Set other properties as needed
                session.save(admin);
            }

            transaction.commit();
            System.out.println("Admin initialization completed.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error initializing admin user: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
