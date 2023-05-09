package repository.user;

import config.ConnectionPool;
import entity.user.Role;
import entity.user.User;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Класс для отправки SQL запросов в базу данных для таблицы @users
 */
public class UserRepository {
    private static BasicDataSource dataSource = ConnectionPool.getDataSource();

    public User findByLogin(String login) throws SQLException {
        String query = "SELECT user_id, login, password, email, role_id FROM users WHERE login = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return User.fromResultSet(result);
                }
            }
        }
        return null;
    }

    public Role findRoleByRoleId(UUID roleId) {
        String query = "SELECT role_id, name FROM roles WHERE role_id = ?";
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setObject(1, roleId);
            try (ResultSet rs = pstmt.executeQuery();) {
                if (rs.next()) {
                    return Role.fromResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            // Обработка ошибок
            throw new RuntimeException(ex);
        }
        return null;
    }

    public void save(User user) {
        String sql = "UPDATE users SET login=?, password=?, email=?, role_id=? WHERE user_id=?";
        try (PreparedStatement statement = ConnectionPool.getConnection().prepareStatement(sql)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setObject(4, user.getRoleId());
            statement.setObject(5, user.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
