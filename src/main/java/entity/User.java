package entity;

import lombok.Getter;

import java.util.UUID;

@Getter
public class User {
    private UUID userId;
    private String login;
    private String password;
    private String email;

    public User(UUID userId, String login, String password, String email) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.email = email;
    }
}
