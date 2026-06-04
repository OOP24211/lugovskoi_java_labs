package com.gogil.sellercrm.adapter.persistence;

import com.gogil.sellercrm.domain.seller.ISellerRepository;
import com.gogil.sellercrm.domain.seller.Seller;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class SellerRepositoryImpl implements ISellerRepository {

    private final SellerJpaRepository jpaRepository;

    public SellerRepositoryImpl(SellerJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Seller save(Seller seller) {
        return jpaRepository.save(seller);
    }

    @Override
    public Optional<Seller> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Seller> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        jpaRepository.findById(id).ifPresent(seller -> {
            seller.setDeletedAt(LocalDateTime.now());
            jpaRepository.save(seller);
        });
    }
}
