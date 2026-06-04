package com.gogil.sellercrm.usecase.seller;

import com.gogil.sellercrm.adapter.dto.SellerResponse;
import com.gogil.sellercrm.domain.seller.ISellerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllSellers {

    private final ISellerRepository sellerRepository;

    public GetAllSellers(ISellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public List<SellerResponse> execute() {
        return sellerRepository.findAll().stream()
                .filter(seller -> seller.isActive())
                .map(seller -> new SellerResponse(
                        seller.getId(),
                        seller.getName(),
                        seller.getContactInfo(),
                        seller.getRegistrationDate(),
                        seller.getCreatedAt(),
                        seller.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }
}
