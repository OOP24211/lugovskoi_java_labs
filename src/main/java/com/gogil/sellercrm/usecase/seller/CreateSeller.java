package com.gogil.sellercrm.usecase.seller;

import com.gogil.sellercrm.adapter.dto.CreateSellerRequest;
import com.gogil.sellercrm.adapter.dto.SellerResponse;
import com.gogil.sellercrm.domain.seller.ISellerRepository;
import com.gogil.sellercrm.domain.seller.Seller;
import com.gogil.sellercrm.domain.wallet.IWalletRepository;
import com.gogil.sellercrm.domain.wallet.Wallet;
import org.springframework.stereotype.Service;

@Service
public class CreateSeller {

    private final ISellerRepository sellerRepository;
    private final IWalletRepository walletRepository;

    public CreateSeller(ISellerRepository sellerRepository, IWalletRepository walletRepository) {
        this.sellerRepository = sellerRepository;
        this.walletRepository = walletRepository;
    }

    public SellerResponse execute(CreateSellerRequest request) {
        Seller seller = new Seller(request.getName(), request.getContactInfo());
        Seller saved = sellerRepository.save(seller);

        Wallet wallet = new Wallet(saved);
        walletRepository.save(wallet);

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
