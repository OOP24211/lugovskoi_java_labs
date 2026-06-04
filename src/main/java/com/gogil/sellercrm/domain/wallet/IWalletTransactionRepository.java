package com.gogil.sellercrm.domain.wallet;

import java.util.List;

public interface IWalletTransactionRepository {

    WalletTransaction save(WalletTransaction walletTransaction);

    List<WalletTransaction> findByWallet(Wallet wallet);
}
