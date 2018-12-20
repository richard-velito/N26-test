package com.n26.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author richard.dbrsac@gmail.com
 */

public class TransactionsPeriod {

    private List<Transaction> transactionList;
    private Statistics statistics;

    private ReadWriteLock lock;

    public TransactionsPeriod () {
        transactionList = new ArrayList<Transaction>();
        statistics = new Statistics();

        lock = new ReentrantReadWriteLock();
    }

    public void add (Transaction transaction) {

        try {
            lock.writeLock().lock();

            statistics.setMin(transaction.getAmount());
            statistics.setMax(transaction.getAmount());
            statistics.setSum(transaction.getAmount());
            statistics.setCount(1);
            statistics.setAvg(statistics.getSum());

            transactionList.add(transaction);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void put (Transaction transaction) {

        try {
            lock.writeLock().lock();

            if (statistics.getMin().compareTo(transaction.getAmount()) == 1){
                statistics.setMin(transaction.getAmount());
            }
            if (statistics.getMax().compareTo(transaction.getAmount()) == -1){
                statistics.setMax(transaction.getAmount());
            }

            statistics.setSum(statistics.getSum().add(transaction.getAmount()));
            statistics.setCount(statistics.getCount() + 1);
            statistics.setAvg(statistics.getSum().divide(new BigDecimal(statistics.getCount()),
                    RoundingMode.HALF_UP));

            transactionList.add(transaction);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public Statistics getStatistics () {
        return statistics;
    }

}
