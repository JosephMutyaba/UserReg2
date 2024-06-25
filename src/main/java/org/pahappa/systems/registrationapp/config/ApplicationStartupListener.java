package org.pahappa.systems.registrationapp.config;

import org.pahappa.systems.registrationapp.util.AdminInitializer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ApplicationStartupListener initialized successfully.");
        // Initialization logic here, e.g., database initialization, etc.
        AdminInitializer.initializeAdmin();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ApplicationStartupListener destroyed successfully.");
        // Cleanup logic here, if needed
    }
}
