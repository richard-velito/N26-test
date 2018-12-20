package com.n26.controller;

import com.n26.domain.Transaction;
import com.n26.dto.TransactionRequest;
import com.n26.exception.NotParsableTransactionException;
import com.n26.exception.OlderTransactionException;
import com.n26.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

/**
 * @author richard.dbrsac@gmail.com
 */
@RestController
public class TransactionController {

    @Autowired
    ConversionService conversionService;

    @Autowired
    private TransactionService transactionService;

    /**
     * This endpoint is called to create a new transaction. It MUST execute in constant time and memory (O(1)).
     * @param transactionRequest
     */
    @PostMapping("/transactions")
    public ResponseEntity add (@RequestBody TransactionRequest transactionRequest) {

        try {
            long currentTimestamp = Instant.now().toEpochMilli();
            Transaction transaction = conversionService.convert(transactionRequest, Transaction.class);
            transactionService.addTransaction(transaction, currentTimestamp);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (NotParsableTransactionException | ConversionFailedException ex) {
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (OlderTransactionException e) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * This endpoint causes all existing transactions to be deleted
     */
    @DeleteMapping("/transactions")
    public ResponseEntity delete () {
        transactionService.deleteTransactions();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
