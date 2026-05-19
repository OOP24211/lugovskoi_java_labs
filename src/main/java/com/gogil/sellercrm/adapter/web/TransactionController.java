package com.gogil.sellercrm.adapter.web;

import com.gogil.sellercrm.adapter.dto.CreateTransactionRequest;
import com.gogil.sellercrm.adapter.dto.TransactionResponse;
import com.gogil.sellercrm.usecase.transaction.CreateTransaction;
import com.gogil.sellercrm.usecase.transaction.GetAllTransactions;
import com.gogil.sellercrm.usecase.transaction.GetSellerTransactions;
import com.gogil.sellercrm.usecase.transaction.GetTransaction;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final CreateTransaction createTransaction;
    private final GetTransaction getTransaction;
    private final GetAllTransactions getAllTransactions;
    private final GetSellerTransactions getSellerTransactions;

    public TransactionController(CreateTransaction createTransaction,
                                 GetTransaction getTransaction,
                                 GetAllTransactions getAllTransactions,
                                 GetSellerTransactions getSellerTransactions) {
        this.createTransaction = createTransaction;
        this.getTransaction = getTransaction;
        this.getAllTransactions = getAllTransactions;
        this.getSellerTransactions = getSellerTransactions;
    }

    @GetMapping
    public List<TransactionResponse> getAll() {
        return getAllTransactions.execute();
    }

    @GetMapping("/{id}")
    public TransactionResponse getById(@PathVariable Long id) {
        return getTransaction.execute(id);
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> create(@Valid @RequestBody CreateTransactionRequest request) {
        TransactionResponse response = createTransaction.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/seller/{sellerId}")
    public List<TransactionResponse> getBySeller(@PathVariable Long sellerId) {
        return getSellerTransactions.execute(sellerId);
    }
}
