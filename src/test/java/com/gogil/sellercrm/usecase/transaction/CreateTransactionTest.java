package com.gogil.sellercrm.usecase.transaction;

import com.gogil.sellercrm.adapter.dto.CreateTransactionRequest;
import com.gogil.sellercrm.adapter.dto.TransactionResponse;
import com.gogil.sellercrm.domain.exception.SellerNotFoundException;
import com.gogil.sellercrm.domain.seller.ISellerRepository;
import com.gogil.sellercrm.domain.seller.Seller;
import com.gogil.sellercrm.domain.transaction.ITransactionRepository;
import com.gogil.sellercrm.domain.transaction.PaymentType;
import com.gogil.sellercrm.domain.transaction.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateTransactionTest {

    @Mock
    private ITransactionRepository transactionRepository;

    @Mock
    private ISellerRepository sellerRepository;

    @InjectMocks
    private CreateTransaction createTransaction;

    @Test
    void shouldCreateTransactionSuccessfully() {
        Seller seller = new Seller("Григорий", "grigory@mail.ru");
        CreateTransactionRequest request = new CreateTransactionRequest(
                1L,
                PaymentType.CARD,
                new BigDecimal("5000")
        );
        Transaction savedTransaction = new Transaction(seller, new BigDecimal("5000"), PaymentType.CARD);

        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);

        TransactionResponse response = createTransaction.execute(request);

        assertNotNull(response);
        assertEquals(new BigDecimal("5000"), response.getAmount());
        assertEquals(PaymentType.CARD, response.getPaymentType());
    }

    @Test
    void shouldThrowExceptionWhenSellerNotFound() {
        CreateTransactionRequest request = new CreateTransactionRequest(
                77L,
                PaymentType.CASH,
                new BigDecimal("1000")
        );
        when(sellerRepository.findById(77L)).thenReturn(Optional.empty());

        assertThrows(SellerNotFoundException.class, () -> createTransaction.execute(request));
    }
}
