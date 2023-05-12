package service.user;

import entity.user.Wallet;
import repository.user.WalletRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.UUID;

public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService() {
        this.walletRepository = new WalletRepository();
    }

    public void replenishBalance(UUID userId, BigDecimal replenishSum) {
        try {
            walletRepository.replenishBalance(userId, replenishSum);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Wallet getWalletByUserId(UUID userId) {
        try {
            return walletRepository.findWalletByUserId(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Wallet userWallet) {
        walletRepository.update(userWallet);
    }
}
