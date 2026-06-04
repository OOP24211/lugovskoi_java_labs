package com.gogil.sellercrm.usecase.wallet;

import com.gogil.sellercrm.adapter.dto.WalletResponse;
import com.gogil.sellercrm.domain.exception.SellerNotFoundException;
import com.gogil.sellercrm.domain.exception.WalletNotFoundException;
import com.gogil.sellercrm.domain.seller.ISellerRepository;
import com.gogil.sellercrm.domain.seller.Seller;
import com.gogil.sellercrm.domain.wallet.IWalletRepository;
import com.gogil.sellercrm.domain.wallet.Wallet;
import org.springframework.stereotype.Service;

@Service
public class GetWallet {

    private final IWalletRepository walletRepository;
    private final ISellerRepository sellerRepository;

    public GetWallet(IWalletRepository walletRepository, ISellerRepository sellerRepository) {
        this.walletRepository = walletRepository;
        this.sellerRepository = sellerRepository;
    }

    public WalletResponse execute(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException(sellerId));

        Wallet wallet = walletRepository.findBySeller(seller)
                .orElseThrow(() -> new WalletNotFoundException(sellerId));

        return new WalletResponse(
                wallet.getId(),
                wallet.getSeller().getId(),
                wallet.getBalance(),
                wallet.getCreatedAt(),
                wallet.getUpdatedAt()
        );
    }
}
