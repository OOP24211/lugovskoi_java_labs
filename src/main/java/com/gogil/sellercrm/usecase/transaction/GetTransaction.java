package com.gogil.sellercrm.usecase.transaction;

import com.gogil.sellercrm.adapter.dto.TransactionResponse;
import com.gogil.sellercrm.domain.exception.TransactionNotFoundException;
import com.gogil.sellercrm.domain.transaction.ITransactionRepository;
import com.gogil.sellercrm.domain.transaction.Transaction;
import org.springframework.stereotype.Service;

@Service
public class GetTransaction {

    private final ITransactionRepository transactionRepository;

    public GetTransaction(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionResponse execute(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));

        return new TransactionResponse(
                transaction.getId(),
                transaction.getSeller().getId(),
                transaction.getAmount(),
                transaction.getPaymentType(),
                transaction.getTransactionDate()
        );
    }
}
