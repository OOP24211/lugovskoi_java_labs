package com.gogil.sellercrm.adapter.dto;

import com.gogil.sellercrm.domain.transaction.PaymentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionRequest {

    @NotNull(message = "Укажите id продавца")
    private Long sellerId;

    @NotNull(message = "Выберите тип оплаты")
    private PaymentType paymentType;

    @Positive(message = "Сумма должна быть больше нуля")
    private BigDecimal amount;
}
