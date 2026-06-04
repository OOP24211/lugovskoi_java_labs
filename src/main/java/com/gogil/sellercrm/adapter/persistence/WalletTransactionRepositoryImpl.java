package com.gogil.sellercrm.adapter.persistence;

import com.gogil.sellercrm.domain.wallet.IWalletTransactionRepository;
import com.gogil.sellercrm.domain.wallet.Wallet;
import com.gogil.sellercrm.domain.wallet.WalletTransaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WalletTransactionRepositoryImpl implements IWalletTransactionRepository {

    private final WalletTransactionJpaRepository jpaRepository;

    public WalletTransactionRepositoryImpl(WalletTransactionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public WalletTransaction save(WalletTransaction walletTransaction) {
        return jpaRepository.save(walletTransaction);
    }

    @Override
    public List<WalletTransaction> findByWallet(Wallet wallet) {
        return jpaRepository.findByWalletOrderByCreatedAtDesc(wallet);
    }
}
