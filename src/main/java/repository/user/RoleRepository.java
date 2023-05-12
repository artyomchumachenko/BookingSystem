package repository.user;

import config.ConnectionPool;
import entity.user.Role;
import repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoleRepository implements Repository<Role> {
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

    public void addLogAboutChangeRole(String adminLogin, String userLogin, String roleName) {
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

    @Override
    public void add(Role item) {
        String sql = "INSERT INTO public.roles (name) VALUES (?)";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, item.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Role item) {
        String sql = "UPDATE public.roles SET name = ? WHERE role_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, item.getName());
            statement.setObject(2, item.getRoleId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Role item) {
        String sql = "DELETE FROM public.roles WHERE role_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, item.getRoleId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Role findById(UUID id) {
        Role role = null;
        String sql = "SELECT * FROM public.roles WHERE role_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                role = Role.fromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

    @Override
    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT * FROM public.roles";
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                roles.add(Role.fromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }
}
