package service;

import entity.User;
import entity.UserCredentials;
import repository.UserRepository;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public void createUser(User user) {
        // Хешируем пароль с использованием алгоритма MD5
        String hashedPassword = null;
        String pass = user.getPassword();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(pass.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            hashedPassword = no.toString(16);
            while (hashedPassword.length() < 32) {
                hashedPassword = "0" + hashedPassword;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        user.setPassword(hashedPassword);
        userRepository.addUser(user);
    }

    public boolean isLoginExist(String username) throws SQLException {
        return userRepository.findByLogin(username) != null;
    }
}
