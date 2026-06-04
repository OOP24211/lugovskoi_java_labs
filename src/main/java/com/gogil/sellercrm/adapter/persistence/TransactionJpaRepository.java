package com.gogil.sellercrm.adapter.persistence;

import com.gogil.sellercrm.domain.seller.Seller;
import com.gogil.sellercrm.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionJpaRepository  extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySeller(Seller seller);

    List<Transaction> findBySellerOrderByTransactionDateAsc(Seller seller);
}
