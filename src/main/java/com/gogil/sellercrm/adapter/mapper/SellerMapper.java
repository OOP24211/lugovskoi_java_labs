package com.gogil.sellercrm.adapter.mapper;

import com.gogil.sellercrm.adapter.dto.SellerResponse;
import com.gogil.sellercrm.adapter.dto.CreateSellerRequest;
import com.gogil.sellercrm.domain.seller.Seller;
import org.springframework.stereotype.Component;

@Component
public class SellerMapper {

    public Seller toDomain(CreateSellerRequest request) {
        return new Seller(request.getName(), request.getContactInfo());
    }

    public SellerResponse toResponse(Seller seller) {
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
