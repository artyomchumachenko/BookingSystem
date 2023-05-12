package repository.country;

import config.ConnectionPool;
import entity.country.City;
import repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CityRepository implements Repository<City> {

    public List<City> all() {
        List<City> cities = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM public.cities");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                cities.add(City.fromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cities;
    }

    @Override
    public void add(City item) {
        String query = "INSERT INTO cities (country_id, name) VALUES (?, ?)";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, item.getCountryId());
            statement.setString(2, item.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(City item) {
        String query = "UPDATE cities SET country_id = ?, name = ? WHERE city_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, item.getCountryId());
            statement.setString(2, item.getName());
            statement.setObject(3, item.getCityId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(City item) {
        String query = "DELETE FROM cities WHERE city_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, item.getCityId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public City findById(UUID id) {
        City city = null;
        String query = "SELECT * FROM cities WHERE city_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                city = City.fromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public List<City> findAll() {
        List<City> cities = new ArrayList<>();
        String query = "SELECT * FROM cities";
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                cities.add(City.fromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }
}
