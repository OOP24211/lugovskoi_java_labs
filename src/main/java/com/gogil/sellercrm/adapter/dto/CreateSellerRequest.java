package com.gogil.sellercrm.adapter.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class CreateSellerRequest {

    @NotBlank(message = "Имя не должно быть пустым")
    private String name;

    @NotBlank(message = "Заполните поле 'контактные данные'")
    private String contactInfo;

}
