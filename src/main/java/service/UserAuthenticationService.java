package service;

import entity.User;
import entity.UserCredentials;
import org.apache.commons.codec.digest.DigestUtils;
import repository.UserRepository;

import java.sql.SQLException;

/**
 * Класс для логической обработки аутентификации пользователя
 */
public class UserAuthenticationService {
    private final UserRepository userRepository;

    public UserAuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean authenticate(UserCredentials credentials) throws SQLException {
        User user = userRepository.findByLogin(credentials.getLogin());
        if (user != null) {
            String hashedPassword = hashPassword(credentials.getPassword());
            return user.getPassword().equals(hashedPassword);
        } else {
            return false;
        }
    }

    private String hashPassword(String password) {
        // В этом методе можно использовать различные алгоритмы шифрования, например, BCrypt или SHA-256.
        // Здесь мы просто используем функцию md5(), как в примере с PostgreSQL.
        return DigestUtils.md5Hex(password);
    }
}
