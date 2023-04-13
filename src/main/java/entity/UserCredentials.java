package entity;

import lombok.Getter;

/**
 * Класс промежуточной сущности User для авторизации
 */
@Getter
public class UserCredentials {
    private String login;
    private String password;

    public UserCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
