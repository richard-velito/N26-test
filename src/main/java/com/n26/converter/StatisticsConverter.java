package com.n26.converter;

import com.n26.domain.Statistics;
import com.n26.dto.StatisticsResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author richard.dbrsac@gmail.com
 */

@Component
public class StatisticsConverter implements Converter<Statistics, StatisticsResponse> {

    public StatisticsResponse convert (Statistics statistics) {
        StatisticsResponse response = new StatisticsResponse();
        response.setAvg(statistics.getAvg().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        response.setMax(statistics.getMax().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        response.setMin(statistics.getMin().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        response.setSum(statistics.getSum().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        response.setCount(statistics.getCount());
        return response;
    }
}
