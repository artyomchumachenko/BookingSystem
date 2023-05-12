package repository.user;

import config.ConnectionPool;
import entity.user.Wallet;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class WalletRepository {
    public void replenishBalance(UUID userId, BigDecimal replenishSum) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getConnection()) {
            statement = connection.prepareStatement("SELECT * FROM wallets WHERE user_id = ?");
            statement.setObject(1, userId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                BigDecimal currentBalance = resultSet.getBigDecimal("balance_rub");
                BigDecimal newBalance = currentBalance.add(replenishSum);

                PreparedStatement updateStatement = connection.prepareStatement("UPDATE wallets SET balance_rub = ? WHERE user_id = ?");
                updateStatement.setBigDecimal(1, newBalance);
                updateStatement.setObject(2, userId);
                updateStatement.executeUpdate();
            } else {
                UUID walletId = UUID.randomUUID();

                PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO wallets (wallet_id, balance_rub, user_id, frozen) VALUES (?, ?, ?, ?)");
                insertStatement.setObject(1, walletId);
                insertStatement.setBigDecimal(2, replenishSum);
                insertStatement.setObject(3, userId);
                insertStatement.setBoolean(4, false);
                insertStatement.executeUpdate();
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }

    public Wallet findWalletByUserId(UUID userId) throws SQLException {
        String query = "SELECT * FROM wallets WHERE user_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Wallet.fromResultSet(resultSet);
                }
                return null; // если кошелек не найден
            }
        }
    }

    public void update(Wallet userWallet) {
        String sql = "UPDATE wallets SET balance_rub = ?, frozen = ? WHERE wallet_id = ?";
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, userWallet.getBalanceRub());
            stmt.setBoolean(2, userWallet.isFrozen());
            stmt.setObject(3, userWallet.getWalletId());
            // Выполнение запроса
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Обработка ошибок
            e.printStackTrace();
        }
    }

}
