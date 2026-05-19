package com.gogil.sellercrm.usecase.seller;

import com.gogil.sellercrm.adapter.dto.SellerResponse;
import com.gogil.sellercrm.adapter.dto.UpdateSellerRequest;
import com.gogil.sellercrm.domain.exception.SellerNotFoundException;
import com.gogil.sellercrm.domain.seller.ISellerRepository;
import com.gogil.sellercrm.domain.seller.Seller;
import org.springframework.stereotype.Service;

@Service
public class UpdateSeller {

    private final ISellerRepository sellerRepository;

    public UpdateSeller(ISellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public SellerResponse execute(Long id, UpdateSellerRequest request) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(()-> new SellerNotFoundException(id));

        seller.setName(request.getName());
        seller.setContactInfo(request.getContactInfo());

        Seller update = sellerRepository.save(seller);

        return new SellerResponse(
                update.getId(),
                update.getName(),
                update.getContactInfo(),
                update.getRegistrationDate()
        );
    }
}
