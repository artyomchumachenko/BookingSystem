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
public class Role {
    private UUID roleId;
    private String name;

    public static Role fromResultSet(ResultSet rs) throws SQLException {
        return Role.builder()
                .roleId((UUID) rs.getObject("role_id"))
                .name(rs.getString("name"))
                .build();
    }
}
