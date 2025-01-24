package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    //    private final  = new ArrayList<>();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            String createTableSQL = """
                    CREATE TABLE IF NOT EXISTS Users (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        last_name VARCHAR(100) NOT NULL,
                        age INT NOT NULL
                    );
                    """;
            statement.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            String dropTableSQL = "DROP TABLE IF EXISTS Users";
            statement.executeUpdate(dropTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insertSQL = "INSERT INTO Users (name, last_name, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String removeSQL = "DELETE FROM Users WHERE id = ?";
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(removeSQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        String selectAllUsers = "SELECT * FROM Users";
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectAllUsers)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("last_name"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                allUsers.add(user);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        String cleanSQL = "DELETE FROM Users";
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(cleanSQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
