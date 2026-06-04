package com.gogil.sellercrm.usecase.seller;

import com.gogil.sellercrm.domain.exception.SellerNotFoundException;
import com.gogil.sellercrm.domain.seller.ISellerRepository;
import com.gogil.sellercrm.domain.seller.Seller;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteSellerTest {

    @Mock
    private ISellerRepository sellerRepository;

    @InjectMocks
    private DeleteSeller deleteSeller;

    @Test
    void shouldSoftDeleteSeller() {
        Seller seller = new Seller("Григорий", "grigory@mail.ru");
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));

        deleteSeller.execute(1L);

        verify(sellerRepository).save(any(Seller.class));
    }

    @Test
    void shouldSoftDeleteSellerNotFound() {
        when(sellerRepository.findById(77L)).thenReturn(Optional.empty());

        assertThrows(SellerNotFoundException.class, () -> deleteSeller.execute(77L));
    }
}
