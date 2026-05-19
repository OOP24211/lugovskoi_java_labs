package com.gogil.sellercrm.domain.transaction;

import com.gogil.sellercrm.domain.seller.Seller;

import java.util.List;
import java.util.Optional;

public interface ITransactionRepository {

    Transaction save(Transaction transaction);

    Optional<Transaction> findById(Long id);

    List<Transaction> findAll();

    List<Transaction> findBySeller(Seller seller);

    List<Transaction> findBySellerOrderByTransactionDateAsc(Seller seller);
}
