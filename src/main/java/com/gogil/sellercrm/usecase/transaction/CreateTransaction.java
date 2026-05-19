package com.gogil.sellercrm.usecase.transaction;

import com.gogil.sellercrm.adapter.dto.CreateTransactionRequest;
import com.gogil.sellercrm.adapter.dto.TransactionResponse;
import com.gogil.sellercrm.domain.exception.SellerNotFoundException;
import com.gogil.sellercrm.domain.seller.ISellerRepository;
import com.gogil.sellercrm.domain.seller.Seller;
import com.gogil.sellercrm.domain.transaction.ITransactionRepository;
import com.gogil.sellercrm.domain.transaction.Transaction;
import org.springframework.stereotype.Service;

@Service
public class CreateTransaction {

    private final ITransactionRepository transactionRepository;
    private final ISellerRepository sellerRepository;

    public CreateTransaction(ITransactionRepository transactionRepository, ISellerRepository sellerRepository) {
        this.transactionRepository = transactionRepository;
        this.sellerRepository = sellerRepository;
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

        return new  TransactionResponse(
                saved.getId(),
                saved.getSeller().getId(),
                saved.getAmount(),
                saved.getPaymentType(),
                saved.getTransactionDate()
        );
    }
}
