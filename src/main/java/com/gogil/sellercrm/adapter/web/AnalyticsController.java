package com.gogil.sellercrm.adapter.web;

import com.gogil.sellercrm.adapter.dto.BestPeriodResponse;
import com.gogil.sellercrm.adapter.dto.SellerResponse;
import com.gogil.sellercrm.domain.analytics.Period;
import com.gogil.sellercrm.usecase.analytics.GetBestPeriod;
import com.gogil.sellercrm.usecase.analytics.GetSellersBelowAmount;
import com.gogil.sellercrm.usecase.analytics.GetTopSeller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final GetTopSeller getTopSeller;
    private final GetSellersBelowAmount getSellersBelowAmount;
    private final GetBestPeriod getBestPeriod;

    public AnalyticsController(GetTopSeller getTopSeller,
                               GetSellersBelowAmount getSellersBelowAmount,
                               GetBestPeriod getBestPeriod) {
        this.getTopSeller = getTopSeller;
        this.getSellersBelowAmount = getSellersBelowAmount;
        this.getBestPeriod = getBestPeriod;
    }

    @GetMapping("/top-seller")
    public SellerResponse getTopSeller(@RequestParam Period period) {
        return getTopSeller.execute(period);
    }

    @GetMapping("/sellers-below")
    public List<SellerResponse> getSellersBelowAmount(@RequestParam Period period, @RequestParam BigDecimal amount) {
        return getSellersBelowAmount.execute(period, amount);
    }

    @GetMapping("/best-period/{sellerId}")
    public BestPeriodResponse getBestPeriod(@PathVariable Long sellerId) {
        return getBestPeriod.execute(sellerId);
    }
}
