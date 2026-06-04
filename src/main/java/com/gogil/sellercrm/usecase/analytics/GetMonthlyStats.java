package com.gogil.sellercrm.usecase.analytics;

import com.gogil.sellercrm.adapter.dto.MonthlyStatsResponse;
import com.gogil.sellercrm.domain.exception.SellerNotFoundException;
import com.gogil.sellercrm.domain.seller.ISellerRepository;
import com.gogil.sellercrm.domain.seller.Seller;
import com.gogil.sellercrm.domain.transaction.ITransactionRepository;
import com.gogil.sellercrm.domain.transaction.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class GetMonthlyStats {

    private final ITransactionRepository transactionRepository;
    private final ISellerRepository sellerRepository;

    public GetMonthlyStats(ITransactionRepository transactionRepository,
                           ISellerRepository sellerRepository) {
        this.transactionRepository = transactionRepository;
        this.sellerRepository = sellerRepository;
    }

    public List<MonthlyStatsResponse> execute(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException(sellerId));

        Map<YearMonth, List<Transaction>> grouped = transactionRepository.findBySeller(seller).stream()
                .filter(Transaction::isActive)
                .collect(Collectors.groupingBy(
                        t -> YearMonth.from(t.getTransactionDate()),
                        TreeMap::new,
                        Collectors.toList()
                ));

        return grouped.entrySet().stream()
                .sorted(Map.Entry.<YearMonth, List<Transaction>>comparingByKey().reversed())
                .map(entry -> {
                    YearMonth ym = entry.getKey();
                    List<Transaction> txs = entry.getValue();
                    BigDecimal total = txs.stream()
                            .map(Transaction::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    String month = ym.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru"))
                            + " " + ym.getYear();
                    return new MonthlyStatsResponse(month, txs.size(), total);
                })
                .collect(Collectors.toList());
    }
}
