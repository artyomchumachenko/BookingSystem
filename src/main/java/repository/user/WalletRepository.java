package repository.user;

import config.ConnectionPool;
import entity.user.Wallet;
import repository.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WalletRepository implements Repository<Wallet> {
    /**
     * Пополнить баланс кошелька пользователя
     */
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

    @Override
    public void add(Wallet item) {
        String sql = "INSERT INTO public.wallets (balance_rub, user_id, frozen) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, item.getBalanceRub());
            statement.setObject(2, item.getUserId());
            statement.setBoolean(3, item.isFrozen());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Wallet item) {
        String sql = "UPDATE public.wallets SET balance_rub = ?, user_id = ?, frozen = ? WHERE wallet_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, item.getBalanceRub());
            statement.setObject(2, item.getUserId());
            statement.setBoolean(3, item.isFrozen());
            statement.setObject(4, item.getWalletId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Wallet item) {
        String sql = "DELETE FROM public.wallets WHERE wallet_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, item.getWalletId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Wallet findById(UUID id) {
        Wallet wallet = null;
        String sql = "SELECT * FROM public.wallets WHERE wallet_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                wallet = Wallet.fromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wallet;
    }

    @Override
    public List<Wallet> findAll() {
        List<Wallet> wallets = new ArrayList<>();
        String sql = "SELECT * FROM public.wallets";
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                wallets.add(Wallet.fromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wallets;
    }
}
