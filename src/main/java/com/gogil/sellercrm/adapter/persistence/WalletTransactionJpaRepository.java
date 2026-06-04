package com.gogil.sellercrm.adapter.persistence;

import com.gogil.sellercrm.domain.wallet.Wallet;
import com.gogil.sellercrm.domain.wallet.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletTransactionJpaRepository extends JpaRepository<WalletTransaction, Long> {

    List<WalletTransaction> findByWalletOrderByCreatedAtDesc(Wallet wallet);
}
