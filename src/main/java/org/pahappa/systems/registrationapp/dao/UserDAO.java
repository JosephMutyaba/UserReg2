package org.pahappa.systems.registrationapp.dao;

import org.hibernate.SessionFactory;
import org.pahappa.systems.registrationapp.config.SessionConfiguration;

public class UserDAO {

    private final SessionFactory sessionFactory;

    public UserDAO() {
        this.sessionFactory = SessionConfiguration.getSessionFactory();
    }
}
