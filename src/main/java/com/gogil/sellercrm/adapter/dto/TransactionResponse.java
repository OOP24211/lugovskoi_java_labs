package com.gogil.sellercrm.adapter.dto;

import com.gogil.sellercrm.domain.transaction.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TransactionResponse {

    private Long id;
    private Long sellerId;
    private BigDecimal amount;
    private PaymentType paymentType;
    private LocalDateTime transactionDate;
}
