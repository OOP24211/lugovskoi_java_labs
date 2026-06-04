package com.gogil.sellercrm.usecase.seller;

import com.gogil.sellercrm.adapter.dto.CreateSellerRequest;
import com.gogil.sellercrm.adapter.dto.SellerResponse;
import com.gogil.sellercrm.domain.seller.ISellerRepository;
import com.gogil.sellercrm.domain.seller.Seller;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateSellerTest {

    @Mock
    private ISellerRepository sellerRepository;

    @InjectMocks
    private CreateSeller createSeller;

    @Test
    void shouldCreateSeller() {
        CreateSellerRequest request = new CreateSellerRequest("Григорий", "grigory@mail.ru");

        Seller savedSeller = new Seller("Григорий", "grigory@mail.ru");

        when(sellerRepository.save(any(Seller.class))).thenReturn(savedSeller);

        SellerResponse response = createSeller.execute(request);

        assertNotNull(response);
        assertEquals("Григорий", response.getName());
        assertEquals("grigory@mail.ru", response.getContactInfo());
    }
}
