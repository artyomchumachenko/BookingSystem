package dbrepository;

import config.ConnectionPool;
import entity.user.UserCredentials;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.UUID;

public class DbUserRepository {

    public boolean authenticateUserSqlFunction(String login, String pass) {
        boolean isAuthenticated = false;
        try (Connection conn = ConnectionPool.getConnection();
             CallableStatement stmt = conn.prepareCall("{? = call authenticate_user(?, ?)}")) {

            stmt.registerOutParameter(1, Types.BOOLEAN);
            stmt.setString(2, login);
            stmt.setString(3, pass);

            stmt.execute();

            isAuthenticated = stmt.getBoolean(1);
            if (isAuthenticated) {
                System.out.println("User authenticated successfully");
            } else {
                System.out.println("Invalid login or password");
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return isAuthenticated;
    }

    public boolean createUserSqlFunction(
            UserCredentials userCredentials, String email
    ) {
        boolean result = false;
        try (Connection conn = ConnectionPool.getConnection();
             CallableStatement stmt = conn.prepareCall("{ ? = call register_user(?, ?, ?, ?, ?) }")) {

            // Задаем параметры функции
            stmt.setObject(2, UUID.randomUUID()); // p_user_id
            stmt.setString(3, userCredentials.getLogin()); // p_login
            stmt.setString(4, userCredentials.getPassword()); // p_password
            stmt.setString(5, userCredentials.getConfirm()); // p_confirm_password
            stmt.setString(6, email); // p_email

            // Регистрируем выходной параметр (возвращает boolean)
            stmt.registerOutParameter(1, Types.BOOLEAN);

            // Выполняем запрос
            stmt.execute();

            // Получаем результат
            result = stmt.getBoolean(1);
            System.out.println("User registration successful: " + result);

        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
        }
        return result;
    }
}
