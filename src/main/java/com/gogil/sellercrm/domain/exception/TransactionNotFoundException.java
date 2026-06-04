package com.gogil.sellercrm.domain.exception;

public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException(Long id) {
        super("Транзакция по id " + id + " не найдена");
    }
}
