package com.gogil.sellercrm.domain.seller;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "sellers")
@Getter
@Setter
@NoArgsConstructor

public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String contactInfo;

    private LocalDateTime registrationDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public Seller(String name, String contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.registrationDate = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return deletedAt == null;
    }
}
