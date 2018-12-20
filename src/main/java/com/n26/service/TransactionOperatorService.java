package com.n26.service;

import com.n26.domain.Statistics;
import com.n26.domain.Transaction;

/**
 * @author richard.dbrsac@gmail.com
 */
public interface TransactionOperatorService {

    void putTransaction (Transaction transaction, long currentTimestamp);

    void reset ();

    Statistics calculate (long currentTimestamp);
}
