package com.gogil.sellercrm.adapter.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateOwnProfileRequest {

    @NotBlank(message = "Заполните поле 'контактные данные'")
    private String contactInfo;
}
