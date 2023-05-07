package entity.user;

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
public class Wallet {
    private UUID walletId;
    private BigDecimal balanceRub;
    private UUID userId;
    private boolean frozen;

    public static Wallet fromResultSet(ResultSet rs) throws SQLException {
        return Wallet.builder()
                .walletId((UUID) rs.getObject("wallet_id"))
                .balanceRub(rs.getBigDecimal("balance_rub"))
                .userId((UUID) rs.getObject("user_id"))
                .frozen(rs.getBoolean("frozen"))
                .build();
    }
}
