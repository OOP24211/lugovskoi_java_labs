package com.gogil.sellercrm.usecase.transaction;

import com.gogil.sellercrm.adapter.dto.TransactionResponse;
import com.gogil.sellercrm.domain.transaction.ITransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllTransactions {

    private final ITransactionRepository transactionRepository;

    public GetAllTransactions(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionResponse> execute() {
        return transactionRepository.findAll().stream()
                .filter(transaction -> transaction.isActive())
                .map(transaction -> new TransactionResponse(
                        transaction.getId(),
                        transaction.getSeller().getId(),
                        transaction.getAmount(),
                        transaction.getPaymentType(),
                        transaction.getTransactionDate()
                ))
                .collect(Collectors.toList());
    }
}
