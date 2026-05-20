package com.gogil.sellercrm.usecase.analytics;

import com.gogil.sellercrm.adapter.dto.SellerResponse;
import com.gogil.sellercrm.domain.analytics.Period;
import com.gogil.sellercrm.domain.seller.ISellerRepository;
import com.gogil.sellercrm.domain.seller.Seller;
import com.gogil.sellercrm.domain.transaction.ITransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;

@Service
public class GetTopSeller {

    private final ISellerRepository sellerRepository;
    private final ITransactionRepository transactionRepository;

    public GetTopSeller(ISellerRepository sellerRepository, ITransactionRepository transactionRepository) {
        this.sellerRepository = sellerRepository;
        this.transactionRepository = transactionRepository;
    }

    public SellerResponse execute(Period period) {
        LocalDateTime from = getPeriodStart(period);

        Seller topSeller = sellerRepository.findAll().stream()
                .filter(seller -> seller.isActive())
                .max(Comparator.comparing(seller -> getTotalAmount(seller, from)))
                .orElseThrow(() -> new RuntimeException("Продавцы не найдены"));

        return new SellerResponse(
                topSeller.getId(),
                topSeller.getName(),
                topSeller.getContactInfo(),
                topSeller.getRegistrationDate(),
                topSeller.getCreatedAt(),
                topSeller.getUpdatedAt()
        );
    }

    private BigDecimal getTotalAmount(Seller seller, LocalDateTime from) {
        return transactionRepository.findBySeller(seller).stream()
                .filter(transaction -> transaction.isActive())
                .filter(transaction -> transaction.getTransactionDate().isAfter(from))
                .map(transaction -> transaction.getAmount())
                .reduce(BigDecimal.ZERO, (sum, amount) -> sum.add(amount));
    }

    private LocalDateTime getPeriodStart(Period period) {
        LocalDateTime now = LocalDateTime.now();
        return switch (period) {
            case DAY -> now.toLocalDate().atStartOfDay();
            case MONTH -> now.withDayOfMonth(1).toLocalDate().atStartOfDay();
            case QUARTER -> getQuarterStart(now);
            case YEAR -> now.withDayOfYear(1).toLocalDate().atStartOfDay();
        };
    }

    private LocalDateTime getQuarterStart(LocalDateTime now) {
        int firstMonthOfQuarter = ((now.getMonthValue() - 1) / 3) * 3 + 1;
        return now.withMonth(firstMonthOfQuarter).withDayOfMonth(1).toLocalDate().atStartOfDay();
    }
}
