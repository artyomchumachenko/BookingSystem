package repository;

import config.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HotelFacilitiesQuery {

    private final Database database;

    public HotelFacilitiesQuery() {
        this.database = new Database();
    }

    public Map<String, Boolean> getFacilitiesByHotelId(UUID hotelId) {
        Map<String, Boolean> facilitiesMap = new HashMap<>();

        try(Connection conn = database.connect();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT f.name, hf.payable_service " +
                            "FROM public.hotel_facilities hf " +
                            "JOIN public.facilities f ON f.f_id = hf.f_id " +
                            "WHERE hf.hotel_id = ?")) {

            stmt.setObject(1, hotelId);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                String facilityName = rs.getString("name");
                boolean isPayable = rs.getBoolean("payable_service");

                facilitiesMap.put(facilityName, isPayable);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return facilitiesMap;
    }
}
