package com.gogil.sellercrm.adapter.mapper;

import com.gogil.sellercrm.adapter.dto.TransactionResponse;
import com.gogil.sellercrm.domain.transaction.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getSeller().getId(),
                transaction.getAmount(),
                transaction.getPaymentType(),
                transaction.getTransactionDate()
        );
    }
}
