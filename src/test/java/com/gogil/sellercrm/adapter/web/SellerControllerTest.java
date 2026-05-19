package com.gogil.sellercrm.adapter.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gogil.sellercrm.adapter.dto.CreateSellerRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SellerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnEmptyListWhenNoSellers() throws Exception {
        mockMvc.perform(get("/api/sellers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldCreateSeller() throws Exception {
        CreateSellerRequest request = new CreateSellerRequest("gogil", "gogil@mail.ru");

        mockMvc.perform(post("/api/sellers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("gogil"))
                .andExpect(jsonPath("$.contactInfo").value("gogil@mail.ru"));
    }

    @Test
    void shouldReturnSellerById() throws Exception {
        CreateSellerRequest request = new CreateSellerRequest("Григорий", "grigory@mail.ru");
        String response = mockMvc.perform(post("/api/sellers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(get("/api/sellers/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Григорий"));
    }

    @Test
    void shouldReturn404WhenSellerNotFound() throws Exception {
        mockMvc.perform(get("/api/sellers/77"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteSeller() throws Exception {
        CreateSellerRequest request = new CreateSellerRequest("gogilugo", "gogilugo@mail.ru");
        String response = mockMvc.perform(post("/api/sellers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(delete("/api/sellers/" + id))
                .andExpect(status().isNoContent());
    }
}
