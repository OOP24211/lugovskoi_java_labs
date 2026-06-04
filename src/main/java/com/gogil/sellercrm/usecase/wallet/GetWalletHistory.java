package com.gogil.sellercrm.usecase.wallet;

import com.gogil.sellercrm.adapter.dto.WalletTransactionResponse;
import com.gogil.sellercrm.domain.exception.SellerNotFoundException;
import com.gogil.sellercrm.domain.exception.WalletNotFoundException;
import com.gogil.sellercrm.domain.seller.ISellerRepository;
import com.gogil.sellercrm.domain.seller.Seller;
import com.gogil.sellercrm.domain.wallet.IWalletRepository;
import com.gogil.sellercrm.domain.wallet.IWalletTransactionRepository;
import com.gogil.sellercrm.domain.wallet.Wallet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetWalletHistory {

    private final IWalletRepository walletRepository;
    private final IWalletTransactionRepository walletTransactionRepository;
    private final ISellerRepository sellerRepository;

    public GetWalletHistory(IWalletRepository walletRepository,
                            IWalletTransactionRepository walletTransactionRepository,
                            ISellerRepository sellerRepository) {
        this.walletRepository = walletRepository;
        this.walletTransactionRepository = walletTransactionRepository;
        this.sellerRepository = sellerRepository;
    }

    public List<WalletTransactionResponse> execute(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException(sellerId));

        Wallet wallet = walletRepository.findBySeller(seller)
                .orElseThrow(() -> new WalletNotFoundException(sellerId));

        return walletTransactionRepository.findByWallet(wallet).stream()
                .map(wt -> new WalletTransactionResponse(
                        wt.getId(),
                        wt.getAmount(),
                        wt.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}
