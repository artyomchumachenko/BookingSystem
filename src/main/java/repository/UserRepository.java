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
    private Database database = new Database();

    public UserRepository() {
    }

    public User findByLogin(String login) throws SQLException {
        String sql = "SELECT user_id, login, password, email FROM users WHERE login = ?";
        Connection connection = database.connect();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    UUID userId = UUID.fromString(result.getString("user_id"));
                    String password = result.getString("password");
                    String email = result.getString("email");
                    return new User(userId, login, password, email);
                } else {
                    return null;
                }
            }
        }
    }

    public void addUser(User user) {
        try (Connection conn = database.connect()) {
            // Создаем запрос для добавления нового пользователя
            String sql = "INSERT INTO public.users(\n" +
                    "\tuser_id, login, password, email)\n" +
                    "\tVALUES (?, ?, ?, ?);";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setObject(1, user.getUserId());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getEmail());

            // Выполняем запрос
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Новый пользователь успешно добавлен!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
