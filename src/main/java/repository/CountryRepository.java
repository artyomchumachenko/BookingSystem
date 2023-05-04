package repository;

import config.ConnectionPool;
import entity.country.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CountryRepository {

    public Country findCountryByCityId(UUID cityId) {
        String query = "SELECT countries.country_id, countries.name " +
                "FROM public.cities " +
                "JOIN public.countries ON cities.country_id = countries.country_id " +
                "WHERE cities.city_id = ?";
        Country country = null;

        try {
            Connection conn = ConnectionPool.getDataSource().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setObject(1, cityId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                country = new Country();
                country.setCountryId(UUID.fromString(rs.getString("country_id")));
                country.setName(rs.getString("name"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return country;
    }
}
