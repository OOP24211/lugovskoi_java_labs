package com.gogil.sellercrm.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BestPeriodResponse {

    private LocalDateTime from;
    private LocalDateTime to;
    private int transactionCount;
}
