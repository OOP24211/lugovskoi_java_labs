package com.gogil.sellercrm.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class MonthlyStatsResponse {

    private String month;
    private int transactionCount;
    private BigDecimal totalAmount;
}
