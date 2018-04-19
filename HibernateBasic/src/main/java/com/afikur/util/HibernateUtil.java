package com.afikur.util;

import com.afikur.model.Employee;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionJavaConfigFactory;

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();

            Properties props = new Properties();
            props.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            props.put("hibernate.connection.url", "jdbc:mysql://localhost/testDB");
            props.put("hibernate.connection.username", "root");
            props.put("hibernate.connection.password", "password");
            props.put("hibernate.current_session_context_class", "thread");
            props.put("hibernate.id.new_generator_mappings", "false");
            props.put("hibernate.hbm2ddl.auto", "create");

            configuration.setProperties(props);

            configuration.addAnnotatedClass(Employee.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

            return configuration.buildSessionFactory(serviceRegistry);
        }

        catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if(sessionJavaConfigFactory == null) sessionJavaConfigFactory = buildSessionFactory();
        return sessionJavaConfigFactory;
    }
}
