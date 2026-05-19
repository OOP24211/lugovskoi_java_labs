package com.gogil.sellercrm.adapter.persistence;

import com.gogil.sellercrm.domain.seller.Seller;
import com.gogil.sellercrm.domain.transaction.ITransactionRepository;
import com.gogil.sellercrm.domain.transaction.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TransactionRepositoryImpl implements ITransactionRepository {

    private final TransactionJpaRepository jpaRepository;

    public TransactionRepositoryImpl(TransactionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        return jpaRepository.save(transaction);
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Transaction> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public List<Transaction> findBySeller(Seller seller) {
        return jpaRepository.findBySeller(seller);
    }

    @Override
    public List<Transaction> findBySellerOrderByTransactionDateAsc(Seller seller) {
        return jpaRepository.findBySellerOrderByTransactionDateAsc(seller);
    }
}
