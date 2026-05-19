package com.gogil.sellercrm.adapter.persistence;

import com.gogil.sellercrm.domain.seller.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerJpaRepository extends JpaRepository<Seller, Long> {
}
