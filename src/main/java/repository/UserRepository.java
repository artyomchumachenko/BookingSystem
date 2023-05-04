package repository;

import config.ConnectionPool;
import entity.user.Role;
import entity.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Класс для отправки SQL запросов в базу данных для таблицы @users
 */
public class UserRepository {

    public User findByLogin(String login) throws SQLException {
        String sql = "SELECT user_id, login, password, email, role_id FROM users WHERE login = ?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
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

    public Role findRoleByRoleId(UUID roleId) {
        String sql = "SELECT role_id, name FROM roles WHERE role_id = ?";
        try (Connection conn = ConnectionPool.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, roleId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Role role = new Role();
                role.setRoleId(rs.getObject("role_id", UUID.class));
                role.setName(rs.getString("name"));
                return role;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            // Обработка ошибок
            throw new RuntimeException(ex);
        }
    }
}
