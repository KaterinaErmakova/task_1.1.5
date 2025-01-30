package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/users";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";
    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();

            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/users?useSSL=false");
            configuration.setProperty("hibernate.connection.username", "root");
            configuration.setProperty("hibernate.connection.password", "root");
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.hbm2ddl.auto", "none");
            configuration.addAnnotatedClass(User.class);

            sessionFactory = configuration.buildSessionFactory(new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());
        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }


    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return connection;
    }


}
