package com.n26.service;

import com.n26.domain.Transaction;
import com.n26.exception.OlderTransactionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.Assert.assertEquals;

/**
 * @author richard.dbrsac@gmail.com
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    TransactionService transactionService;

    @Test
    public void testBasic () {
        assertEquals(
                "class com.n26.service.TransactionServiceImpl", this.transactionService.getClass().toString());
    }

    @Test(expected = OlderTransactionException.class)
    public void testAddTransactionCheckOlderTransaction () {
        long timeStamp = Instant.now().toEpochMilli() + ( 600 * 1000 );
        transactionService.addTransaction(createTransaction(), timeStamp);
    }

    @Test(expected = OlderTransactionException.class)
    public void testAddTransactionCheckOlderTransactionByOneSecond () {
        long timeStamp = Instant.now().toEpochMilli() + ( 61 * 1000 );
        transactionService.addTransaction(createTransaction(), timeStamp);
    }

    @Test
    public void testAddTransaction () {
        transactionService.addTransaction(createTransaction(), Instant.now().toEpochMilli());
    }

    @Test(expected = NullPointerException.class)
    public void testAddNullTransaction () {
        transactionService.addTransaction(null, Instant.now().toEpochMilli());
    }

    @Test
    public void testAddZeroTimestamp () {
        transactionService.addTransaction(createTransaction(), 0);
    }

    private Transaction createTransaction () {
        Transaction transaction = new Transaction();
            transaction.setTimestamp(Instant.now().toEpochMilli());
            transaction.setAmount(new BigDecimal(100.00));
        return transaction;
    }
}
