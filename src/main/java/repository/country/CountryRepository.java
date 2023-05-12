package repository.country;

import config.ConnectionPool;
import entity.country.Country;
import repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CountryRepository implements Repository<Country> {

    public Country findByCityId(UUID cityId) {
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

    @Override
    public void add(Country item) {
        String sql = "INSERT INTO public.countries (name) VALUES (?)";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, item.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Country item) {
        String sql = "UPDATE public.countries SET name = ? WHERE country_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, item.getName());
            statement.setObject(2, item.getCountryId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Country item) {
        String sql = "DELETE FROM public.countries WHERE country_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, item.getCountryId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Country findById(UUID id) {
        Country country = null;
        String sql = "SELECT * FROM public.countries WHERE country_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                country = Country.fromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    @Override
    public List<Country> findAll() {
        List<Country> countries = new ArrayList<>();
        String sql = "SELECT * FROM public.countries";
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                countries.add(Country.fromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countries;
    }
}
