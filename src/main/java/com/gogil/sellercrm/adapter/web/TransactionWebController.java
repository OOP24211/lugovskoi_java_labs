package com.gogil.sellercrm.adapter.web;

import com.gogil.sellercrm.adapter.dto.SellerResponse;
import com.gogil.sellercrm.adapter.dto.TransactionResponse;
import com.gogil.sellercrm.usecase.seller.GetAllSellers;
import com.gogil.sellercrm.usecase.transaction.GetAllTransactions;
import com.gogil.sellercrm.usecase.transaction.GetSellerTransactions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/transactions")
public class TransactionWebController {

    private final GetAllTransactions getAllTransactions;
    private final GetSellerTransactions getSellerTransactions;
    private final GetAllSellers getAllSellers;

    public TransactionWebController(GetAllTransactions getAllTransactions,
                                    GetSellerTransactions getSellerTransactions,
                                    GetAllSellers getAllSellers) {
        this.getAllTransactions = getAllTransactions;
        this.getSellerTransactions = getSellerTransactions;
        this.getAllSellers = getAllSellers;
    }

    @GetMapping
    public String list(@RequestParam(required = false) Long sellerId, Model model) {
        List<TransactionResponse> transactions;

        if (sellerId != null) {
            transactions = getSellerTransactions.execute(sellerId);
        } else {
            transactions = getAllTransactions.execute();
        }

        Map<Long, String> sellerNames = getAllSellers.execute().stream()
                .collect(Collectors.toMap(SellerResponse::getId, SellerResponse::getName));

        model.addAttribute("transactions", transactions);
        model.addAttribute("sellers", getAllSellers.execute());
        model.addAttribute("sellerNames", sellerNames);
        model.addAttribute("selectedSellerId", sellerId);
        return "transactions/list";
    }
}
