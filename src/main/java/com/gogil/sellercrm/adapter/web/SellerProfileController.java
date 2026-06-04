package com.gogil.sellercrm.adapter.web;

import com.gogil.sellercrm.adapter.dto.CreateOwnTransactionRequest;
import com.gogil.sellercrm.adapter.dto.CreateTransactionRequest;
import com.gogil.sellercrm.adapter.dto.TransactionResponse;
import com.gogil.sellercrm.adapter.dto.UpdateOwnProfileRequest;
import com.gogil.sellercrm.adapter.security.UserDetailsImpl;
import com.gogil.sellercrm.domain.transaction.PaymentType;
import com.gogil.sellercrm.usecase.analytics.GetBestPeriod;
import com.gogil.sellercrm.usecase.analytics.GetMonthlyStats;
import com.gogil.sellercrm.usecase.seller.GetSeller;
import com.gogil.sellercrm.usecase.seller.UpdateOwnProfile;
import com.gogil.sellercrm.usecase.transaction.CreateTransaction;
import com.gogil.sellercrm.usecase.transaction.GetSellerTransactions;
import com.gogil.sellercrm.usecase.wallet.GetWallet;
import com.gogil.sellercrm.usecase.wallet.GetWalletHistory;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/my")
public class SellerProfileController {

    private final GetSeller getSeller;
    private final GetSellerTransactions getSellerTransactions;
    private final GetWallet getWallet;
    private final GetWalletHistory getWalletHistory;
    private final GetBestPeriod getBestPeriod;
    private final GetMonthlyStats getMonthlyStats;
    private final CreateTransaction createTransaction;
    private final UpdateOwnProfile updateOwnProfile;

    public SellerProfileController(GetSeller getSeller,
                                   GetSellerTransactions getSellerTransactions,
                                   GetWallet getWallet,
                                   GetWalletHistory getWalletHistory,
                                   GetBestPeriod getBestPeriod,
                                   GetMonthlyStats getMonthlyStats,
                                   CreateTransaction createTransaction,
                                   UpdateOwnProfile updateOwnProfile) {
        this.getSeller = getSeller;
        this.getSellerTransactions = getSellerTransactions;
        this.getWallet = getWallet;
        this.getWalletHistory = getWalletHistory;
        this.getBestPeriod = getBestPeriod;
        this.getMonthlyStats = getMonthlyStats;
        this.createTransaction = createTransaction;
        this.updateOwnProfile = updateOwnProfile;
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        Long sellerId = userDetails.getUser().getSeller().getId();
        model.addAttribute("seller", getSeller.execute(sellerId));
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("updateRequest", new UpdateOwnProfileRequest());
        return "my/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                @Valid @ModelAttribute("updateRequest") UpdateOwnProfileRequest request,
                                BindingResult result,
                                Model model) {
        Long sellerId = userDetails.getUser().getSeller().getId();

        if (result.hasErrors()) {
            model.addAttribute("seller", getSeller.execute(sellerId));
            model.addAttribute("username", userDetails.getUsername());
            return "my/profile";
        }

        updateOwnProfile.execute(sellerId, request);
        return "redirect:/my/profile?updated";
    }

    @GetMapping("/transactions")
    public String transactions(@AuthenticationPrincipal UserDetailsImpl userDetails,
                               @RequestParam(required = false) String paymentType,
                               @RequestParam(required = false) String from,
                               @RequestParam(required = false) String to,
                               Model model) {
        Long sellerId = userDetails.getUser().getSeller().getId();
        List<TransactionResponse> transactions = getSellerTransactions.execute(sellerId);

        if (paymentType != null && !paymentType.isBlank()) {
            transactions = transactions.stream()
                    .filter(t -> t.getPaymentType().name().equals(paymentType))
                    .toList();
        }
        if (from != null && !from.isBlank()) {
            LocalDate fromDate = LocalDate.parse(from);
            transactions = transactions.stream()
                    .filter(t -> !t.getTransactionDate().toLocalDate().isBefore(fromDate))
                    .toList();
        }
        if (to != null && !to.isBlank()) {
            LocalDate toDate = LocalDate.parse(to);
            transactions = transactions.stream()
                    .filter(t -> !t.getTransactionDate().toLocalDate().isAfter(toDate))
                    .toList();
        }

        model.addAttribute("transactions", transactions);
        model.addAttribute("paymentTypes", PaymentType.values());
        model.addAttribute("selectedPaymentType", paymentType);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("request", new CreateOwnTransactionRequest());
        return "my/transactions";
    }

    @PostMapping("/transactions")
    public String createTransaction(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @Valid @ModelAttribute("request") CreateOwnTransactionRequest request,
                                    BindingResult result,
                                    Model model) {
        Long sellerId = userDetails.getUser().getSeller().getId();

        if (result.hasErrors()) {
            model.addAttribute("transactions", getSellerTransactions.execute(sellerId));
            model.addAttribute("paymentTypes", PaymentType.values());
            return "my/transactions";
        }

        CreateTransactionRequest transactionRequest = new CreateTransactionRequest(
                sellerId,
                request.getPaymentType(),
                request.getAmount()
        );
        createTransaction.execute(transactionRequest);
        return "redirect:/my/transactions";
    }

    @GetMapping("/wallet")
    public String wallet(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        Long sellerId = userDetails.getUser().getSeller().getId();
        model.addAttribute("wallet", getWallet.execute(sellerId));
        model.addAttribute("history", getWalletHistory.execute(sellerId));
        return "my/wallet";
    }

    @GetMapping("/analytics")
    public String analytics(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        Long sellerId = userDetails.getUser().getSeller().getId();
        List<TransactionResponse> transactions = getSellerTransactions.execute(sellerId);

        BigDecimal totalRevenue = transactions.stream()
                .map(TransactionResponse::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal avgAmount = transactions.isEmpty()
                ? BigDecimal.ZERO
                : totalRevenue.divide(BigDecimal.valueOf(transactions.size()), 2, RoundingMode.HALF_UP);

        model.addAttribute("transactionCount", transactions.size());
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("avgAmount", avgAmount);
        model.addAttribute("monthlyStats", getMonthlyStats.execute(sellerId));

        try {
            model.addAttribute("bestPeriod", getBestPeriod.execute(sellerId));
        } catch (RuntimeException e) {
            model.addAttribute("bestPeriodError", e.getMessage());
        }

        return "my/analytics";
    }
}
