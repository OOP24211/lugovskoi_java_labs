package com.gogil.sellercrm.usecase.seller;

import com.gogil.sellercrm.adapter.dto.CreateSellerRequest;
import com.gogil.sellercrm.adapter.dto.SellerResponse;
import com.gogil.sellercrm.domain.seller.ISellerRepository;
import com.gogil.sellercrm.domain.seller.Seller;
import org.springframework.stereotype.Service;

@Service
public class CreateSeller {

    private final ISellerRepository sellerRepository;

    public CreateSeller(ISellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public SellerResponse execute(CreateSellerRequest request) {
        Seller seller = new Seller(request.getName(), request.getContactInfo());
        Seller saved = sellerRepository.save(seller);
        return new SellerResponse(
                saved.getId(),
                saved.getName(),
                saved.getContactInfo(),
                saved.getRegistrationDate(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );
    }
}
