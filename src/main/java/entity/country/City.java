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
public class City {
    private UUID cityId;
    private UUID countryId;
    private String name;

    /**
     * Создает объект City из ResultSet.
     *
     * @param rs ResultSet, содержащий результаты запроса.
     * @return Объект City, заполненный значениями из ResultSet.
     * @throws SQLException если возникает ошибка при чтении значений из ResultSet.
     */
    public static City fromResultSet(ResultSet rs) throws SQLException {
        return City.builder()
                .cityId(UUID.fromString(rs.getString("city_id")))
                .countryId(UUID.fromString(rs.getString("country_id")))
                .name(rs.getString("name"))
                .build();
    }
}
