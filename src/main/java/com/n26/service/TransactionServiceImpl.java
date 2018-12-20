package com.n26.service;

import com.n26.domain.Transaction;
import com.n26.exception.OlderTransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * @author richard.dbrsac@gmail.com
 */

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger LOGGER =
            Logger.getLogger( TransactionServiceImpl.class.getName() );
    private static final int SIXTY_SECONDS = 60 * 1000;

    @Autowired
    TransactionOperatorService transactionOperatorService;

    public void addTransaction(Transaction transaction, long currentTimestamp) {
        checkOlderTransaction(transaction, currentTimestamp);
        transactionOperatorService.putTransaction(transaction, currentTimestamp);
    }

    public void deleteTransactions() {
        transactionOperatorService.reset();
    }

    private void checkOlderTransaction (Transaction transaction, long currentTimestamp) {
        long sixtySecondsAgo = currentTimestamp - SIXTY_SECONDS;
        if (transaction.getTimestamp() < sixtySecondsAgo) {
            LOGGER.info("Older Transaction Exception");
            throw new OlderTransactionException("Older Transaction Exception");
        }
    }
}
