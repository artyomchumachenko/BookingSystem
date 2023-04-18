package service;

import entity.User;
import entity.UserCredentials;
import repository.UserRepository;
import repositoryfordb.DbUserRepository;

import java.sql.SQLException;
import java.util.UUID;

public class UserService {
    private final UserRepository userRepository;
    private DbUserRepository dbUserRepository = new DbUserRepository();

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public User authenticate(String login, String password) throws SQLException {
        UserCredentials userCredentials = new UserCredentials(login, password);
        UserAuthenticationService userAuthenticationService = new UserAuthenticationService(this.userRepository);

        if (userAuthenticationService.dbAuthenticate(userCredentials)) {
            return userRepository.findByLogin(login);
        }
        return null;
    }

    public boolean isNewUserCreated(
            UserCredentials userCredentials,
            String email
    ) {
        return dbUserRepository.isNewUserCreated(
                userCredentials, email
        );
    }

    public User findByLogin(String login) throws SQLException {
        return userRepository.findByLogin(login);
    }

    public String getRoleById(UUID roleId) {
        return userRepository.getRoleNameByRoleId(roleId);
    }
}
