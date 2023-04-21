package entity.user;

import lombok.Getter;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Класс промежуточной сущности User для авторизации
 */
@Getter
public class UserCredentials {
    private String login;
    private String password;
    private String confirm;

    public UserCredentials(String login, String password, String confirm) {
        this.login = login;
        this.password = hashPassword(password);
        this.confirm = hashPassword(confirm);
    }

    public UserCredentials(String login, String password) {
        this.login = login;
        this.password = hashPassword(password);
        this.confirm = hashPassword(password);
    }

    private String hashPassword(String password) {
        // В этом методе можно использовать различные алгоритмы шифрования, например, BCrypt или SHA-256.
        // Здесь мы просто используем функцию md5(), как в примере с PostgreSQL.
        return DigestUtils.md5Hex(password);
    }
}
