package service.user;

import entity.user.Role;
import entity.user.User;
import entity.user.UserCredentials;
import repository.user.UserRepository;
import dbrepository.DbUserRepository;

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

    public boolean isUserCreated(
            UserCredentials userCredentials,
            String email
    ) {
        return dbUserRepository.createUserSqlFunction(
                userCredentials, email
        );
    }

    public User getUserByLogin(String login) throws SQLException {
        return userRepository.findByLogin(login);
    }

    public Role getRoleById(UUID roleId) {
        return userRepository.findRoleByRoleId(roleId);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }
}
