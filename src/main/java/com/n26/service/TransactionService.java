package com.n26.service;

import com.n26.domain.Transaction;

/**
 * @author richard.dbrsac@gmail.com
 */
public interface TransactionService {

    void addTransaction (Transaction transaction, long currentTimestamp);

    void deleteTransactions ();
}
