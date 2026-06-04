package com.gogil.sellercrm.domain.exception;

public class SellerNotFoundException extends RuntimeException {

    public SellerNotFoundException(Long id){
        super("Продавец с id " + id + "не найден");
    }
}
