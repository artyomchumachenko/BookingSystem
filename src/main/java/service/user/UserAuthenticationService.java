package service.user;

import entity.user.User;
import entity.user.UserCredentials;
import repository.UserRepository;
import repositoryfordb.user.DbUserRepository;

import java.sql.SQLException;

/**
 * Класс для логической обработки аутентификации пользователя
 */
public class UserAuthenticationService {
    private final UserRepository userRepository;
    private DbUserRepository dbUserRepository = new DbUserRepository();

    public UserAuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean authenticate(UserCredentials credentials) throws SQLException {
        User user = userRepository.findByLogin(credentials.getLogin());
        if (user != null) {
            return user.getPassword().equals(credentials.getPassword());
        } else {
            return false;
        }
    }

    public boolean dbAuthenticate(UserCredentials credentials) {
        return dbUserRepository.authenticateUser(credentials.getLogin(), credentials.getPassword());
    }
}
