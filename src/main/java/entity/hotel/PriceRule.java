package entity.hotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceRule {
    private UUID ruleId;
    private BigDecimal adultPricePerDay;
    private BigDecimal kidPricePerDay;
    private UUID roomTypeId;

    public static PriceRule fromResultSet(ResultSet rs) throws SQLException {
        return PriceRule.builder()
                .ruleId((UUID) rs.getObject("rule_id"))
                .adultPricePerDay(rs.getBigDecimal("adult_price_day"))
                .kidPricePerDay(rs.getBigDecimal("kid_price_day"))
                .roomTypeId((UUID) rs.getObject("room_type_id"))
                .build();
    }
}
