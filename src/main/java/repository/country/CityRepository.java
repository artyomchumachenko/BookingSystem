package repository.country;

import config.ConnectionPool;
import entity.country.City;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CityRepository {

    /**
     * Ищет город по идентификатору.
     *
     * @param cityId Идентификатор города.
     * @return Объект City, соответствующий указанному идентификатору города.
     * @throws SQLException если возникает ошибка при выполнении запроса к базе данных.
     */
    public City findCityById(UUID cityId) {
        String query = "SELECT city_id, country_id, name FROM cities WHERE city_id = ?";

        try (PreparedStatement statement = ConnectionPool.getConnection().prepareStatement(query)) {
            statement.setObject(1, cityId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return City.fromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
