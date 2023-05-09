package repository.user;

import config.ConnectionPool;
import entity.user.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RoleRepository {
    public Role findRoleByName(String roleName) {
        String sql = "SELECT * FROM roles WHERE name = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, roleName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Role.fromResultSet(resultSet);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Role findRoleById(UUID roleId) {
        String query = "SELECT * FROM roles WHERE role_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, roleId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Role.fromResultSet(resultSet);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendLogAboutChangeRole(String adminLogin, String userLogin, String roleName) {
        String message = "Администратор: " + adminLogin + " изменил роль: " + userLogin + " на " + roleName;
        String query = "INSERT INTO logs (date, operation_id, message) VALUES (CURRENT_TIMESTAMP, ?, ?)";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, 2);
            statement.setString(2, message);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
