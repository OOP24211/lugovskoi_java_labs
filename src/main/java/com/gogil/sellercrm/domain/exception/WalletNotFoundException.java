package com.gogil.sellercrm.domain.exception;

public class WalletNotFoundException extends RuntimeException {

    public WalletNotFoundException(Long sellerId) {
        super("Кошелёк продавца с id " + sellerId + " не найден");
    }
}
