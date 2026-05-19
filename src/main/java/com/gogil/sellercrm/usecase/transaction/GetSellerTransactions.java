
package com.gogil.sellercrm.usecase.transaction;

import com.gogil.sellercrm.adapter.dto.TransactionResponse;
import com.gogil.sellercrm.domain.exception.SellerNotFoundException;
import com.gogil.sellercrm.domain.seller.ISellerRepository;
import com.gogil.sellercrm.domain.seller.Seller;
import com.gogil.sellercrm.domain.transaction.ITransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetSellerTransactions {

    private final ITransactionRepository transactionRepository;
    private final ISellerRepository sellerRepository;

    public GetSellerTransactions(ITransactionRepository transactionRepository, ISellerRepository sellerRepository) {
        this.transactionRepository = transactionRepository;
        this.sellerRepository = sellerRepository;
    }

    public List<TransactionResponse> execute(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException(sellerId));

        return transactionRepository.findBySeller(seller).stream()
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
