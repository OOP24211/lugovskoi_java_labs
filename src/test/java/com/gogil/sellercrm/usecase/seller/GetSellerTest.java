package com.gogil.sellercrm.usecase.seller;

import com.gogil.sellercrm.adapter.dto.SellerResponse;
import com.gogil.sellercrm.domain.exception.SellerNotFoundException;
import com.gogil.sellercrm.domain.seller.ISellerRepository;
import com.gogil.sellercrm.domain.seller.Seller;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetSellerTest {

    @Mock
    private ISellerRepository sellerRepository;

    @InjectMocks
    private GetSeller getSeller;

    @Test
    void shouldReturnSellerFound(){
        Seller seller = new Seller("Григорий", "grigory@mail.ru");
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));

        SellerResponse response = getSeller.execute(1L);

        assertNotNull(response);
        assertEquals("Григорий", response.getName());
    }

    @Test
    void shouldReturnSellerNotFound(){
        when(sellerRepository.findById(77L)).thenReturn(Optional.empty());

        assertThrows(SellerNotFoundException.class, () -> getSeller.execute(77L));
    }
}
