package repository;

import config.Database;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

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


}
