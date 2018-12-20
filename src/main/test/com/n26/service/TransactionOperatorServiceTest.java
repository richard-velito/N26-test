package com.n26.service;

import com.n26.domain.Transaction;
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
public class TransactionOperatorServiceTest {

    @Autowired
    TransactionOperatorService transactionOperatorService;

    @Test
    public void testBasic() {
        assertEquals(
                "class com.n26.service.TransactionOperatorServiceImpl", this.transactionOperatorService.getClass().toString());
    }

    @Test
    public void testPutTransaction () {

        Transaction transaction = createTransaction(new BigDecimal(100.00),
                Instant.now().toEpochMilli()-(50*1000));
        long currentTimestamp = Instant.now().toEpochMilli();
        transactionOperatorService.putTransaction(transaction, currentTimestamp);
    }

    @Test
    public void testSuccessCalculateCount () {

        transactionOperatorService.reset();

        long currentTimestamp = Instant.now().toEpochMilli();

        Transaction transaction1 = createTransaction(new BigDecimal(100.00), currentTimestamp-(50*1000));
        Transaction transaction2 = createTransaction(new BigDecimal(120.00), currentTimestamp-(45*1000));
        Transaction transaction3 = createTransaction(new BigDecimal(140.00), currentTimestamp-(44*1000));

        transactionOperatorService.putTransaction(transaction1, currentTimestamp);
        transactionOperatorService.putTransaction(transaction2, currentTimestamp);
        transactionOperatorService.putTransaction(transaction3, currentTimestamp);

        assertEquals(
                transactionOperatorService.calculate(currentTimestamp).getCount(), 3);
    }

    @Test
    public void testSuccessCalculateSum () {

        transactionOperatorService.reset();

        long currentTimestamp = Instant.now().toEpochMilli();

        Transaction transaction1 = createTransaction(new BigDecimal(100.00), currentTimestamp-(50*1000));
        Transaction transaction2 = createTransaction(new BigDecimal(120.00), currentTimestamp-(45*1000));
        Transaction transaction3 = createTransaction(new BigDecimal(140.00), currentTimestamp-(44*1000));

        transactionOperatorService.putTransaction(transaction1, currentTimestamp);
        transactionOperatorService.putTransaction(transaction2, currentTimestamp);
        transactionOperatorService.putTransaction(transaction3, currentTimestamp);

        assertEquals(
                transactionOperatorService.calculate(currentTimestamp).getSum(), new BigDecimal(360.00));
    }

    @Test
    public void testSuccessCalculateMax () {

        transactionOperatorService.reset();

        long currentTimestamp = Instant.now().toEpochMilli();

        Transaction transaction1 = createTransaction(new BigDecimal(100.00), currentTimestamp-(50*1000));
        Transaction transaction2 = createTransaction(new BigDecimal(120.00), currentTimestamp-(45*1000));
        Transaction transaction3 = createTransaction(new BigDecimal(140.00), currentTimestamp-(44*1000));

        transactionOperatorService.putTransaction(transaction1, currentTimestamp);
        transactionOperatorService.putTransaction(transaction2, currentTimestamp);
        transactionOperatorService.putTransaction(transaction3, currentTimestamp);

        assertEquals(
                transactionOperatorService.calculate(currentTimestamp).getMax(), new BigDecimal(140.00));
    }

    @Test
    public void testSuccessCalculateMin () {

        transactionOperatorService.reset();

        long currentTimestamp = Instant.now().toEpochMilli();

        Transaction transaction1 = createTransaction(new BigDecimal(100.00), currentTimestamp-(50*1000));
        Transaction transaction2 = createTransaction(new BigDecimal(120.00), currentTimestamp-(45*1000));
        Transaction transaction3 = createTransaction(new BigDecimal(140.00), currentTimestamp-(44*1000));

        transactionOperatorService.putTransaction(transaction1, currentTimestamp);
        transactionOperatorService.putTransaction(transaction2, currentTimestamp);
        transactionOperatorService.putTransaction(transaction3, currentTimestamp);

        assertEquals(
                transactionOperatorService.calculate(currentTimestamp).getMin(), new BigDecimal(100.00));
    }

    private Transaction createTransaction (BigDecimal amount, long timestamp) {
        Transaction transaction = new Transaction();
        transaction.setTimestamp(timestamp);
        transaction.setAmount(amount);
        return transaction;
    }
}
