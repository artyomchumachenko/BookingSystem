package repository.user;

import config.ConnectionPool;
import entity.user.Role;
import entity.user.User;
import org.apache.commons.dbcp2.BasicDataSource;
import repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс для отправки SQL запросов в базу данных для таблицы @users
 */
public class UserRepository implements Repository<User> {

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

    // TODO УБРАТЬ ЭТО ГОВНО
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

    @Override
    public void add(User item) {
        String sql = "INSERT INTO public.users (login, password, email, role_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, item.getLogin());
            statement.setString(2, item.getPassword());
            statement.setString(3, item.getEmail());
            statement.setObject(4, item.getRoleId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User item) {
        String sql = "UPDATE public.users SET login = ?, password = ?, email = ?, role_id = ? WHERE user_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, item.getLogin());
            statement.setString(2, item.getPassword());
            statement.setString(3, item.getEmail());
            statement.setObject(4, item.getRoleId());
            statement.setObject(5, item.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(User item) {
        String sql = "DELETE FROM public.users WHERE user_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, item.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findById(UUID id) {
        User user = null;
        String sql = "SELECT * FROM public.users WHERE user_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = User.fromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM public.users";
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                users.add(User.fromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

}
