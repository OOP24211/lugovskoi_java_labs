package com.gogil.sellercrm.adapter.web;

import com.gogil.sellercrm.adapter.dto.WalletResponse;
import com.gogil.sellercrm.usecase.wallet.GetWallet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sellers")
public class WalletController {

    private final GetWallet getWallet;

    public WalletController(GetWallet getWallet) {
        this.getWallet = getWallet;
    }

    @GetMapping("/{sellerId}/wallet")
    public WalletResponse getWallet(@PathVariable Long sellerId) {
        return getWallet.execute(sellerId);
    }
}
