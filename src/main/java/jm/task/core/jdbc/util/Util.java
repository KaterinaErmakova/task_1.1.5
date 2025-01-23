package jm.task.core.jdbc.util;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.*;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/users";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";
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
