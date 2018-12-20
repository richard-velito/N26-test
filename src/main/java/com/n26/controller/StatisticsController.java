package com.n26.controller;

import com.n26.dto.StatisticsResponse;
import com.n26.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

/**
 * @author richard.dbrsac@gmail.com
 */

@RestController
public class StatisticsController {

    @Autowired
    ConversionService conversionService;

    @Autowired
    private StatisticsService statisticsService;

    /**
     * This endpoint returns the statistics based on the transactions that happened in the last 60 seconds.
     * It MUST execute in constant time and memory (O(1)).
     */
    @GetMapping("/statistics")
    public StatisticsResponse get () {
        long currentTimestamp = Instant.now().toEpochMilli();
        return conversionService.convert(statisticsService.getStatistics(currentTimestamp), StatisticsResponse.class);
    }
}
