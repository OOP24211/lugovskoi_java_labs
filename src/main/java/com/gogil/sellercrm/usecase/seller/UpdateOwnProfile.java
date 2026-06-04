package com.gogil.sellercrm.usecase.seller;

import com.gogil.sellercrm.adapter.dto.UpdateOwnProfileRequest;
import com.gogil.sellercrm.domain.exception.SellerNotFoundException;
import com.gogil.sellercrm.domain.seller.ISellerRepository;
import com.gogil.sellercrm.domain.seller.Seller;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UpdateOwnProfile {

    private final ISellerRepository sellerRepository;

    public UpdateOwnProfile(ISellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public void execute(Long sellerId, UpdateOwnProfileRequest request) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException(sellerId));

        seller.setContactInfo(request.getContactInfo());
        seller.setUpdatedAt(LocalDateTime.now());
        sellerRepository.save(seller);
    }
}
