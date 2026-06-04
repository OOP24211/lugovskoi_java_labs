package com.gogil.sellercrm.adapter.persistence;

import com.gogil.sellercrm.domain.seller.Seller;
import com.gogil.sellercrm.domain.wallet.IWalletRepository;
import com.gogil.sellercrm.domain.wallet.Wallet;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class WalletRepositoryImpl implements IWalletRepository {

    private final WalletJpaRepository jpaRepository;

    public WalletRepositoryImpl(WalletJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Wallet save(Wallet wallet) {
        return jpaRepository.save(wallet);
    }

    @Override
    public Optional<Wallet> findBySeller(Seller seller) {
        return jpaRepository.findBySeller(seller);
    }
}
