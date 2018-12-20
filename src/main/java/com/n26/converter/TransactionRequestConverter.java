package com.n26.converter;

import com.n26.domain.Transaction;
import com.n26.dto.TransactionRequest;
import com.n26.exception.NotParsableTransactionException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author richard.dbrsac@gmail.com
 */

@Component
public class TransactionRequestConverter implements Converter<TransactionRequest, Transaction> {

    public Transaction convert (TransactionRequest transactionRequest) {

        long requestTimestamp = Instant.parse(transactionRequest.getTimestamp()).toEpochMilli();
        long currentTimestamp = Instant.now().toEpochMilli();

        if (requestTimestamp > currentTimestamp)
            throw new NotParsableTransactionException("Request Timestamp is greater than current Timestamp.");

        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal(transactionRequest.getAmount()));
        transaction.setTimestamp(requestTimestamp);
        return transaction;
    }
}
