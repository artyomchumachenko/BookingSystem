package entity.country;

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
public class Country {
    private UUID countryId;
    private String name;

    public static Country fromResultSet(ResultSet rs) throws SQLException {
        return Country.builder()
                .countryId(UUID.fromString(rs.getString("country_id")))
                .name(rs.getString("name"))
                .build();
    }
}
