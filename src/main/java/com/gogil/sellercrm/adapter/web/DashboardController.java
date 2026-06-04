package com.gogil.sellercrm.adapter.web;

import com.gogil.sellercrm.adapter.dto.TransactionResponse;
import com.gogil.sellercrm.domain.analytics.Period;
import com.gogil.sellercrm.usecase.analytics.GetTopSeller;
import com.gogil.sellercrm.usecase.seller.GetAllSellers;
import com.gogil.sellercrm.usecase.transaction.GetAllTransactions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class DashboardController {

    private final GetAllSellers getAllSellers;
    private final GetAllTransactions getAllTransactions;
    private final GetTopSeller getTopSeller;

    public DashboardController(GetAllSellers getAllSellers,
                               GetAllTransactions getAllTransactions,
                               GetTopSeller getTopSeller) {
        this.getAllSellers = getAllSellers;
        this.getAllTransactions = getAllTransactions;
        this.getTopSeller = getTopSeller;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<TransactionResponse> transactions = getAllTransactions.execute();

        BigDecimal totalRevenue = transactions.stream()
                .map(TransactionResponse::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("sellerCount", getAllSellers.execute().size());
        model.addAttribute("transactionCount", transactions.size());
        model.addAttribute("totalRevenue", totalRevenue);

        try {
            model.addAttribute("topSellerToday", getTopSeller.execute(Period.DAY));
        } catch (RuntimeException e) {
            model.addAttribute("topSellerToday", null);
        }

        return "dashboard";
    }
}
