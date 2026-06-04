package com.gogil.sellercrm.usecase.seller;

import com.gogil.sellercrm.adapter.dto.SellerResponse;
import com.gogil.sellercrm.domain.exception.SellerNotFoundException;
import com.gogil.sellercrm.domain.seller.ISellerRepository;
import com.gogil.sellercrm.domain.seller.Seller;
import org.springframework.stereotype.Service;

@Service
public class GetSeller {

    private final ISellerRepository sellerRepository;

    public GetSeller(ISellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public SellerResponse execute(Long id) {
        Seller seller = sellerRepository.findById(id)
            .orElseThrow(() -> new SellerNotFoundException(id));

        return new SellerResponse(
                seller.getId(),
                seller.getName(),
                seller.getContactInfo(),
                seller.getRegistrationDate(),
                seller.getCreatedAt(),
                seller.getUpdatedAt()
        );
    }
}

