package com.gogil.sellercrm.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class WalletTransactionResponse {

    private Long id;
    private BigDecimal amount;
    private LocalDateTime createdAt;
}
