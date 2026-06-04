package com.gogil.sellercrm.adapter.dto;

import com.gogil.sellercrm.domain.transaction.PaymentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOwnTransactionRequest {

    @NotNull(message = "Выберите тип оплаты")
    private PaymentType paymentType;

    @Positive(message = "Сумма должна быть больше нуля")
    private BigDecimal amount;
}
