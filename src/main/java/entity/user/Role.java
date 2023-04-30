package entity.user;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Role {
    private UUID roleId;
    private String name;

    public Role() {}

    public Role(UUID roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }
}
