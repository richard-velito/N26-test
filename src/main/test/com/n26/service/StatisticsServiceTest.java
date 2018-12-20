package com.n26.service;

import com.n26.domain.Statistics;
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
public class StatisticsServiceTest {

    @Autowired
    StatisticsService statisticsService;

    @Test
    public void testBasic () {
        assertEquals(
                "class com.n26.service.StatisticsServiceImpl", this.statisticsService.getClass().toString());
    }

    @Test
    public void testNoTransactionsCount () {
        Statistics statistics = createEmptyStatistics();
        assertEquals(statisticsService.getStatistics(Instant.now().toEpochMilli()).getCount(), statistics.getCount());
    }

    @Test
    public void testNoTransactionsMin () {
        Statistics statistics = createEmptyStatistics();
        assertEquals(statisticsService.getStatistics(Instant.now().toEpochMilli()).getMin(), statistics.getMin());
    }

    @Test
    public void testNoTransactionsMax () {
        Statistics statistics = createEmptyStatistics();
        assertEquals(statisticsService.getStatistics(Instant.now().toEpochMilli()).getMax(), statistics.getMax());
    }

    @Test
    public void testNoTransactionsAvg () {
        Statistics statistics = createEmptyStatistics();
        assertEquals(statisticsService.getStatistics(Instant.now().toEpochMilli()).getAvg(), statistics.getAvg());
    }

    @Test
    public void testNoTransactionsSum () {
        Statistics statistics = createEmptyStatistics();
        assertEquals(statisticsService.getStatistics(Instant.now().toEpochMilli()).getSum(), statistics.getSum());
    }

    @Test
    public void testNoTransactionsWrongTimestamp () {
        assertEquals(statisticsService.getStatistics(0).getCount(), 0);
    }

    @Test
    public void testNoTransactionsFutureTimestamp () {
        long timeStamp = Instant.now().toEpochMilli() + ( 600 * 1000 );
        assertEquals(statisticsService.getStatistics(timeStamp).getCount(), 0);
    }

    private Statistics createEmptyStatistics () {
        Statistics statistics = new Statistics();
        statistics.setSum(new BigDecimal(0));
        statistics.setAvg(new BigDecimal(0));
        statistics.setMax(new BigDecimal(0));
        statistics.setMin(new BigDecimal(0));
        return statistics;
    }

}
