package com.gogil.sellercrm.adapter.web;

import com.gogil.sellercrm.adapter.dto.CreateSellerRequest;
import com.gogil.sellercrm.adapter.dto.SellerResponse;
import com.gogil.sellercrm.adapter.dto.UpdateSellerRequest;
import com.gogil.sellercrm.usecase.seller.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    private final CreateSeller createSeller;
    private final GetSeller getSeller;
    private final GetAllSellers getAllSellers;
    private final DeleteSeller deleteSeller;
    private final UpdateSeller updateSeller;

    public SellerController(CreateSeller createSeller,
                            GetSeller getSeller,
                            GetAllSellers getAllSellers,
                            DeleteSeller deleteSeller,
                            UpdateSeller updateSeller) {
        this.createSeller = createSeller;
        this.getSeller = getSeller;
        this.getAllSellers = getAllSellers;
        this.deleteSeller = deleteSeller;
        this.updateSeller = updateSeller;
    }

    @GetMapping
    public List<SellerResponse> getAll() {
        return getAllSellers.execute();
    }

    @GetMapping("/{id}")
    public SellerResponse getById(@PathVariable Long id) {
        return getSeller.execute(id);
    }

    @PostMapping
    public ResponseEntity<SellerResponse> create(@Valid @RequestBody CreateSellerRequest request) {
        SellerResponse response = createSeller.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public SellerResponse update(@PathVariable Long id, @Valid @RequestBody UpdateSellerRequest request) {
        return updateSeller.execute(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteSeller.execute(id);
        return ResponseEntity.noContent().build();
    }
}
