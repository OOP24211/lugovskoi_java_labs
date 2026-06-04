package com.gogil.sellercrm.usecase.analytics;

import com.gogil.sellercrm.adapter.dto.BestPeriodResponse;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetBestPeriodTest {

    @Mock
    private ISellerRepository sellerRepository;

    @Mock
    private ITransactionRepository transactionRepository;

    @InjectMocks
    private GetBestPeriod getBestPeriod;

    @Test
    void shouldReturnBestPeriodWithTwoClusters() {
        Seller seller = new Seller("Григорий", "grigory@mail.ru");
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));

        Transaction t1 = new Transaction(seller, new BigDecimal("1000"), PaymentType.CARD);
        Transaction t2 = new Transaction(seller, new BigDecimal("2000"), PaymentType.CASH);
        Transaction t3 = new Transaction(seller, new BigDecimal("3000"), PaymentType.CARD);
        Transaction t4 = new Transaction(seller, new BigDecimal("4000"), PaymentType.CASH);
        Transaction t5 = new Transaction(seller, new BigDecimal("5000"), PaymentType.CARD);

        t1.setTransactionDate(LocalDateTime.of(2026, 1, 1, 10, 0));
        t2.setTransactionDate(LocalDateTime.of(2026, 1, 2, 10, 0));
        t3.setTransactionDate(LocalDateTime.of(2026, 1, 3, 10, 0));
        t4.setTransactionDate(LocalDateTime.of(2026, 2, 1, 10, 0));
        t5.setTransactionDate(LocalDateTime.of(2026, 2, 2, 10, 0));

        when(transactionRepository.findBySellerOrderByTransactionDateAsc(seller))
                .thenReturn(List.of(t1, t2, t3, t4, t5));

        BestPeriodResponse response = getBestPeriod.execute(1L);

        assertNotNull(response);
        assertEquals(3, response.getTransactionCount());
    }

    @Test
    void shouldThrowExceptionWhenSellerNotFound() {
        when(sellerRepository.findById(77L)).thenReturn(Optional.empty());

        assertThrows(SellerNotFoundException.class, () -> getBestPeriod.execute(77L));
    }
}
