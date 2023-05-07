package repository.country;

import config.ConnectionPool;
import entity.country.Country;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CountryRepository {
    private static BasicDataSource dataSource = ConnectionPool.getDataSource();

    public Country findCountryByCityId(UUID cityId) {
        String query = "SELECT * " +
                "FROM public.countries " +
                "INNER JOIN public.cities ON countries.country_id = cities.country_id " +
                "WHERE cities.city_id = ?";

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, cityId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Country.fromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
