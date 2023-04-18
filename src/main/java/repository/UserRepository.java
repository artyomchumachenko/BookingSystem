package repository;

import config.Database;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Класс для отправки SQL запросов в базу данных для таблицы @users
 */
public class UserRepository {
    private final Database database;

    public UserRepository() {
        this.database = new Database();
    }

    public User findByLogin(String login) throws SQLException {
        String sql = "SELECT user_id, login, password, email, role_id FROM users WHERE login = ?";
        Connection connection = database.connect();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    UUID userId = UUID.fromString(result.getString("user_id"));
                    String password = result.getString("password");
                    String email = result.getString("email");
                    UUID roleId = UUID.fromString(result.getString("role_id"));
                    return new User(userId, login, password, email, roleId);
                } else {
                    return null;
                }
            }
        }
    }

    public String getRoleNameByRoleId(UUID roleId) {
        String roleName = null;
        PreparedStatement statement = null;
        try {
            statement = database.connect().prepareStatement("SELECT name FROM roles WHERE role_id = ?");
            statement.setObject(1, roleId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                roleName = result.getString("name");
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roleName;
    }
}
