package com.gogil.sellercrm.usecase.transaction;

import com.gogil.sellercrm.adapter.dto.CreateTransactionRequest;
import com.gogil.sellercrm.adapter.dto.TransactionResponse;
import com.gogil.sellercrm.domain.exception.SellerNotFoundException;
import com.gogil.sellercrm.domain.exception.WalletNotFoundException;
import com.gogil.sellercrm.domain.seller.ISellerRepository;
import com.gogil.sellercrm.domain.seller.Seller;
import com.gogil.sellercrm.domain.transaction.ITransactionRepository;
import com.gogil.sellercrm.domain.transaction.Transaction;
import com.gogil.sellercrm.domain.wallet.IWalletRepository;
import com.gogil.sellercrm.domain.wallet.IWalletTransactionRepository;
import com.gogil.sellercrm.domain.wallet.Wallet;
import com.gogil.sellercrm.domain.wallet.WalletTransaction;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateTransaction {

    private final ITransactionRepository transactionRepository;
    private final ISellerRepository sellerRepository;
    private final IWalletRepository walletRepository;
    private final IWalletTransactionRepository walletTransactionRepository;

    public CreateTransaction(ITransactionRepository transactionRepository,
                             ISellerRepository sellerRepository,
                             IWalletRepository walletRepository,
                             IWalletTransactionRepository walletTransactionRepository) {
        this.transactionRepository = transactionRepository;
        this.sellerRepository = sellerRepository;
        this.walletRepository = walletRepository;
        this.walletTransactionRepository = walletTransactionRepository;
    }

    public TransactionResponse execute(CreateTransactionRequest request) {
        Seller seller = sellerRepository.findById(request.getSellerId())
                .orElseThrow(() -> new SellerNotFoundException(request.getSellerId()));

        Transaction transaction = new Transaction(
                seller,
                request.getAmount(),
                request.getPaymentType()
        );
        Transaction saved = transactionRepository.save(transaction);

        Wallet wallet = walletRepository.findBySeller(seller)
                .orElseThrow(() -> new WalletNotFoundException(seller.getId()));
        wallet.setBalance(wallet.getBalance().add(request.getAmount()));
        wallet.setUpdatedAt(LocalDateTime.now());
        walletRepository.save(wallet);

        WalletTransaction walletTransaction = new WalletTransaction(wallet, request.getAmount());
        walletTransactionRepository.save(walletTransaction);

        return new TransactionResponse(
                saved.getId(),
                saved.getSeller().getId(),
                saved.getAmount(),
                saved.getPaymentType(),
                saved.getTransactionDate()
        );
    }
}
