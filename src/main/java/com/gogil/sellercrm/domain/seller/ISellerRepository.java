package com.gogil.sellercrm.domain.seller;

import java.util.List;
import java.util.Optional;

public interface ISellerRepository {

    Seller save(Seller seller);

    Optional<Seller> findById(Long id);

    List<Seller> findAll();

    void delete(Long id);
}
