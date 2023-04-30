package service.user;

import entity.user.UserCredentials;
import repository.UserRepository;
import repositoryfordb.user.DbUserRepository;

/**
 * Класс логической обработки аутентификации пользователя
 */
public class UserAuthenticationService {
    private final UserRepository userRepository;

    public UserAuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean dbAuthenticate(UserCredentials credentials) {
        DbUserRepository dbUserRepository = new DbUserRepository();
        return dbUserRepository.authenticateUserSqlFunction(credentials.getLogin(), credentials.getPassword());
    }
}
