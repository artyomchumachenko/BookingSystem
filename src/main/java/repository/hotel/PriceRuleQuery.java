package repository.hotel;

import config.ConnectionPool;
import entity.hotel.PriceRule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PriceRuleQuery {
    public PriceRule findPriceRuleByHotelIdAndRoomId(UUID hotelId, UUID roomId) {
        String sql = "SELECT * FROM price_rules WHERE hotel_id = ? AND room_type_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, hotelId);
            statement.setObject(2, roomId);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return PriceRule.fromResultSet(result);
                } else return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
