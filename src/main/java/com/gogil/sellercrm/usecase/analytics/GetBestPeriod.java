package com.gogil.sellercrm.usecase.analytics;

import com.gogil.sellercrm.adapter.dto.BestPeriodResponse;
import com.gogil.sellercrm.domain.exception.SellerNotFoundException;
import com.gogil.sellercrm.domain.seller.ISellerRepository;
import com.gogil.sellercrm.domain.seller.Seller;
import com.gogil.sellercrm.domain.transaction.ITransactionRepository;
import com.gogil.sellercrm.domain.transaction.Transaction;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class GetBestPeriod {

    private final ISellerRepository sellerRepository;
    private final ITransactionRepository transactionRepository;

    public GetBestPeriod(ISellerRepository sellerRepository, ITransactionRepository transactionRepository) {
        this.sellerRepository = sellerRepository;
        this.transactionRepository = transactionRepository;
    }

    public BestPeriodResponse execute(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException(sellerId));

        List<Transaction> transactions = transactionRepository.findBySellerOrderByTransactionDateAsc(seller);

        if (transactions.isEmpty()) {
            throw new RuntimeException("У данного продавца нет транзакций");
        }

        if (transactions.size() < 3) {
            return new BestPeriodResponse(
                    transactions.get(0).getTransactionDate(),
                    transactions.get(transactions.size() -1).getTransactionDate(),
                    transactions.size()
            );
        }

        List<Long> gaps = calculateGaps(transactions);
        long threshold = calculateThreshold(gaps);

        int bestStart = 0;
        int bestEnd = 0;
        int bestCount = 1;
        int curStart = 0;

        for (int i = 1; i < transactions.size(); i++) {
            if (gaps.get(i-1) > threshold) {
                curStart = i;
            }
            int curCount = i - curStart + 1;
            if (curCount > bestCount) {
                bestCount = curCount;
                bestStart = curStart;
                bestEnd = i;
            }
        }

        return new BestPeriodResponse(
                transactions.get(bestStart).getTransactionDate(),
                transactions.get(bestEnd).getTransactionDate(),
                bestCount
        );
    }

    private List<Long> calculateGaps(List<Transaction> transactions) {
        List<Long> gaps = new ArrayList<>();
        for (int i = 1; i < transactions.size(); i++) {
            long hours = Duration.between(
                    transactions.get(i-1).getTransactionDate(),
                    transactions.get(i).getTransactionDate()
            ).toHours();
            gaps.add(hours);
        }
        return gaps;
    }

    private long calculateThreshold(List<Long> gaps) {
        long[] sorted = gaps.stream()
                .mapToLong(gap -> gap)
                .sorted()
                .toArray();
        int index = (int) Math.ceil(0.75 * sorted.length) - 1;
        long percentile75 = sorted[index];
        return Math.max(24, percentile75 * 2);
    }
}
