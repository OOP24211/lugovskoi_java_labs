package com.gogil.sellercrm.adapter.persistence;

import com.gogil.sellercrm.domain.seller.Seller;
import com.gogil.sellercrm.domain.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletJpaRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findBySeller(Seller seller);
}
