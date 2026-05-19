package com.gogil.sellercrm.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SellerResponse {

    private Long id;
    private String name;
    private String contactInfo;
    private LocalDateTime registrationDate;
}
