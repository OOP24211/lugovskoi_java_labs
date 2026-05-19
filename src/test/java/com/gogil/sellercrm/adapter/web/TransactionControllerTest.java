package com.gogil.sellercrm.adapter.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gogil.sellercrm.adapter.dto.CreateSellerRequest;
import com.gogil.sellercrm.adapter.dto.CreateTransactionRequest;
import com.gogil.sellercrm.domain.transaction.PaymentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnEmptyListWhenNoTransactions() throws Exception {
        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldCreateTransaction() throws Exception {
        CreateSellerRequest sellerRequest = new CreateSellerRequest("gogil", "gogil@mail.ru");
        String sellerResponse = mockMvc.perform(post("/api/sellers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sellerRequest)))
                .andReturn().getResponse().getContentAsString();

        Long sellerId = objectMapper.readTree(sellerResponse).get("id").asLong();

        CreateTransactionRequest request = new CreateTransactionRequest(
                sellerId,
                PaymentType.CARD,
                new BigDecimal("5000")
        );

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(5000))
                .andExpect(jsonPath("$.paymentType").value("CARD"));
    }

    @Test
    void shouldReturn404WhenTransactionNotFound() throws Exception {
        mockMvc.perform(get("/api/transactions/77"))
                .andExpect(status().isNotFound());
    }
}
