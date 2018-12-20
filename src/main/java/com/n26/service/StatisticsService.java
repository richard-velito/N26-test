package com.n26.service;

import com.n26.domain.Statistics;

/**
 * @author richard.dbrsac@gmail.com
 */
public interface StatisticsService {

    Statistics getStatistics (long currentTimestamp);
}
