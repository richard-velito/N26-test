package com.n26.service;

import com.n26.domain.Statistics;
import com.n26.domain.Transaction;
import com.n26.domain.TransactionsPeriod;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author richard.dbrsac@gmail.com
 */

@Service
public class TransactionOperatorServiceImpl implements TransactionOperatorService {

    private static final int RANGE_SECONDS = 59;

    private final Map<Long, TransactionsPeriod> transactionsPeriodMap;

    private ReadWriteLock lock;

    public TransactionOperatorServiceImpl () {
        transactionsPeriodMap = new ConcurrentHashMap<Long, TransactionsPeriod>();

        lock = new ReentrantReadWriteLock();
    }

    public void putTransaction (Transaction transaction, long currentTimestamp) {

        Long key = TimeUnit.MILLISECONDS.toSeconds(transaction.getTimestamp());

        if (transactionsPeriodMap.containsKey(key)) {
            transactionsPeriodMap.get(key).put(transaction);
        } else {
            TransactionsPeriod transactionsPeriod = new TransactionsPeriod();
            transactionsPeriod.add(transaction);
            transactionsPeriodMap.put(key, transactionsPeriod);
        }
    }

    public void reset () {
        transactionsPeriodMap.clear();
    }

    public Statistics calculate (long currentTimestamp) {

        Statistics statistics = createStatistics();

        try {
            lock.readLock().lock();

            if (transactionsPeriodMap.size() > 0) {

                long timeTo = TimeUnit.MILLISECONDS.toSeconds(currentTimestamp);
                long timeFrom = timeTo - RANGE_SECONDS;

                for (long i = timeFrom; i <= timeTo; i++) {
                    if (transactionsPeriodMap.containsKey(i)) {

                        TransactionsPeriod period = transactionsPeriodMap.get(i);
                        Statistics periodStatistics = period.getStatistics();
                        if ((statistics.getMin().compareTo(new BigDecimal(0)) == 0) ||
                                statistics.getMin().compareTo(periodStatistics.getMin()) == 1) {
                            statistics.setMin(periodStatistics.getMin());
                        }
                        if (statistics.getMax().compareTo(periodStatistics.getMax()) == -1) {
                            statistics.setMax(periodStatistics.getMax());
                        }
                        statistics.setSum(statistics.getSum().add(periodStatistics.getSum()));
                        statistics.setCount(statistics.getCount() + periodStatistics.getCount());
                        statistics.setAvg(statistics.getSum().divide(new BigDecimal(statistics.getCount()), 2,
                                RoundingMode.HALF_UP));
                    }
                }
            }
        } finally {
            lock.readLock().unlock();
        }
        return statistics;
    }

    private Statistics createStatistics () {
        Statistics statistics = new Statistics();
            statistics.setSum(new BigDecimal(0));
            statistics.setAvg(new BigDecimal(0));
            statistics.setMax(new BigDecimal(0));
            statistics.setMin(new BigDecimal(0));
        return statistics;
    }
}
