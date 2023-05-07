package entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private UUID userId;
    private String login;
    private String password;
    private String email;
    private UUID roleId;

    public static User fromResultSet(ResultSet rs) throws SQLException {
        return User.builder()
                .userId((UUID) rs.getObject("user_id"))
                .login(rs.getString("login"))
                .password(rs.getString("password"))
                .email(rs.getString("email"))
                .roleId((UUID) rs.getObject("role_id"))
                .build();
    }
}
