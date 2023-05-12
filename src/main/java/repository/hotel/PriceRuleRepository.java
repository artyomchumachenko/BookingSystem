package repository.hotel;

import config.ConnectionPool;
import entity.hotel.PriceRule;
import repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PriceRuleRepository implements Repository<PriceRule> {
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

    @Override
    public void add(PriceRule item) {
        String sql = "INSERT INTO public.price_rules (adult_price_day, kid_price_day, room_type_id, hotel_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, item.getAdultPricePerDay());
            statement.setBigDecimal(2, item.getKidPricePerDay());
            statement.setObject(3, item.getRoomTypeId());
            statement.setObject(4, item.getHotelId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(PriceRule item) {
        String sql = "UPDATE public.price_rules SET adult_price_day = ?, kid_price_day = ?, room_type_id = ?, hotel_id = ? WHERE rule_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, item.getAdultPricePerDay());
            statement.setBigDecimal(2, item.getKidPricePerDay());
            statement.setObject(3, item.getRoomTypeId());
            statement.setObject(4, item.getHotelId());
            statement.setObject(5, item.getRuleId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(PriceRule item) {
        String sql = "DELETE FROM public.price_rules WHERE rule_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, item.getRuleId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PriceRule findById(UUID id) {
        PriceRule priceRule = null;
        String sql = "SELECT * FROM public.price_rules WHERE rule_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                priceRule = PriceRule.fromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return priceRule;
    }

    @Override
    public List<PriceRule> findAll() {
        List<PriceRule> priceRules = new ArrayList<>();
        String sql = "SELECT * FROM public.price_rules";
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                priceRules.add(PriceRule.fromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return priceRules;
    }
}
