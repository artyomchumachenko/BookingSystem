package service.user;

import entity.user.UserCredentials;
import repository.user.UserRepository;
import dbrepository.DbUserRepository;

/**
 * Класс логической обработки аутентификации пользователя
 */
public class UserAuthenticationService {

    public boolean dbAuthenticate(UserCredentials credentials) {
        DbUserRepository dbUserRepository = new DbUserRepository();
        return dbUserRepository.authenticateUserSqlFunction(credentials.getLogin(), credentials.getPassword());
    }
}
