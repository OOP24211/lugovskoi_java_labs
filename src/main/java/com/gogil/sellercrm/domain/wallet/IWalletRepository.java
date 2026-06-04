package com.gogil.sellercrm.domain.wallet;

import com.gogil.sellercrm.domain.seller.Seller;

import java.util.Optional;

public interface IWalletRepository {

    Wallet save(Wallet wallet);

    Optional<Wallet> findBySeller(Seller seller);
}
