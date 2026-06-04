package com.gogil.sellercrm.adapter.web;

import com.gogil.sellercrm.domain.analytics.Period;
import com.gogil.sellercrm.usecase.analytics.GetBestPeriod;
import com.gogil.sellercrm.usecase.analytics.GetSellersBelowAmount;
import com.gogil.sellercrm.usecase.analytics.GetTopSeller;
import com.gogil.sellercrm.usecase.seller.GetAllSellers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@RequestMapping("/analytics")
public class AnalyticsWebController {

    private final GetTopSeller getTopSeller;
    private final GetSellersBelowAmount getSellersBelowAmount;
    private final GetBestPeriod getBestPeriod;
    private final GetAllSellers getAllSellers;

    public AnalyticsWebController(GetTopSeller getTopSeller,
                                  GetSellersBelowAmount getSellersBelowAmount,
                                  GetBestPeriod getBestPeriod,
                                  GetAllSellers getAllSellers) {
        this.getTopSeller = getTopSeller;
        this.getSellersBelowAmount = getSellersBelowAmount;
        this.getBestPeriod = getBestPeriod;
        this.getAllSellers = getAllSellers;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("periods", Period.values());
        model.addAttribute("sellers", getAllSellers.execute());
        return "analytics/index";
    }

    @GetMapping("/top-seller")
    public String topSeller(@RequestParam Period period, Model model) {
        model.addAttribute("periods", Period.values());
        model.addAttribute("sellers", getAllSellers.execute());
        model.addAttribute("selectedPeriod", period);

        try {
            model.addAttribute("topSeller", getTopSeller.execute(period));
        } catch (RuntimeException e) {
            model.addAttribute("topSellerError", e.getMessage());
        }

        return "analytics/index";
    }

    @GetMapping("/sellers-below")
    public String sellersBelow(@RequestParam Period period,
                               @RequestParam BigDecimal amount,
                               Model model) {
        model.addAttribute("periods", Period.values());
        model.addAttribute("sellers", getAllSellers.execute());
        model.addAttribute("belowPeriod", period);
        model.addAttribute("belowAmount", amount);

        try {
            model.addAttribute("sellersBelowResult", getSellersBelowAmount.execute(period, amount));
        } catch (RuntimeException e) {
            model.addAttribute("sellersBelowError", e.getMessage());
        }

        return "analytics/index";
    }

    @GetMapping("/best-period")
    public String bestPeriod(@RequestParam Long sellerId, Model model) {
        model.addAttribute("periods", Period.values());
        model.addAttribute("sellers", getAllSellers.execute());
        model.addAttribute("bestPeriodSellerId", sellerId);

        try {
            model.addAttribute("bestPeriodResult", getBestPeriod.execute(sellerId));
        } catch (RuntimeException e) {
            model.addAttribute("bestPeriodError", e.getMessage());
        }

        return "analytics/index";
    }
}
