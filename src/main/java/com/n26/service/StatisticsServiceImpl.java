package com.n26.service;

import com.n26.domain.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author richard.dbrsac@gmail.com
 */

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    TransactionOperatorService transactionOperatorService;

    public Statistics getStatistics(long currentTimestamp) {
        return transactionOperatorService.calculate(currentTimestamp);
    }
}
