package com.gogil.sellercrm.usecase.seller;

import com.gogil.sellercrm.domain.exception.SellerNotFoundException;
import com.gogil.sellercrm.domain.seller.ISellerRepository;
import com.gogil.sellercrm.domain.seller.Seller;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DeleteSeller {

    private final ISellerRepository sellerRepository;

    public DeleteSeller(ISellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public void execute(Long id){
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(()-> new SellerNotFoundException(id));

        seller.setDeletedAt(LocalDateTime.now());
        sellerRepository.save(seller);
    }
}
