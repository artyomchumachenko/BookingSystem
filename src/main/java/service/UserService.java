package service;

import entity.User;
import entity.UserCredentials;
import repository.UserRepository;

import java.sql.SQLException;

public class UserService {
    private UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(String login, String password) throws SQLException {
        UserCredentials userCredentials = new UserCredentials(login, password);
        UserAuthenticationService userAuthenticationService = new UserAuthenticationService(this.userRepository);

        if (userAuthenticationService.authenticate(userCredentials)) {
            return userRepository.findByLogin(login);
        }
        return null;
    }
}
