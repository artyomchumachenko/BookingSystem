package repository.hotel;

import config.ConnectionPool;
import entity.hotel.HotelFacility;
import repository.Repository;

import java.sql.*;
import java.util.*;

public class HotelFacilityRepository implements Repository<HotelFacility> {

    public Map<String, Boolean> findFacilitiesByHotelId(UUID hotelId) {
        String query = "SELECT f.name, hf.payable_service " +
                "FROM public.hotel_facilities hf " +
                "JOIN public.facilities f ON f.f_id = hf.f_id " +
                "WHERE hf.hotel_id = ?";
        Map<String, Boolean> facilitiesMap = new HashMap<>();

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, hotelId);
            try (ResultSet rs = stmt.executeQuery();) {
                while (rs.next()) {
                    String facilityName = rs.getString("name");
                    boolean isPayable = rs.getBoolean("payable_service");

                    facilitiesMap.put(facilityName, isPayable);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facilitiesMap;
    }

    @Override
    public void add(HotelFacility item) {
        String sql = "INSERT INTO public.hotel_facilities (hotel_id, f_id, payable_service) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, item.getHotelId());
            statement.setObject(2, item.getFId());
            statement.setBoolean(3, item.isPayableService());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(HotelFacility item) {
        String sql = "UPDATE public.hotel_facilities SET hotel_id = ?, f_id = ?, payable_service = ? WHERE h_f_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, item.getHotelId());
            statement.setObject(2, item.getFId());
            statement.setBoolean(3, item.isPayableService());
            statement.setObject(4, item.getHfId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(HotelFacility item) {
        String sql = "DELETE FROM public.hotel_facilities WHERE h_f_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, item.getHfId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HotelFacility findById(UUID id) {
        HotelFacility hotelFacility = null;
        String sql = "SELECT * FROM public.hotel_facilities WHERE h_f_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                hotelFacility = HotelFacility.fromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotelFacility;
    }

    @Override
    public List<HotelFacility> findAll() {
        List<HotelFacility> hotelFacilities = new ArrayList<>();
        String sql = "SELECT * FROM public.hotel_facilities";
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                hotelFacilities.add(HotelFacility.fromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotelFacilities;
    }
}
