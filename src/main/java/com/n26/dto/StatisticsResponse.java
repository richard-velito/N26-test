package com.n26.dto;

import java.io.Serializable;

/**
 * @author richard.dbrsac@gmail.com
 */

public class StatisticsResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sum;
    private String avg;
    private String max;
    private String min;
    private long count;

    public String getSum () {
        return sum;
    }

    public void setSum (String sum) {
        this.sum = sum;
    }

    public String getAvg () {
        return avg;
    }

    public void setAvg (String avg) {
        this.avg = avg;
    }

    public String getMax () {
        return max;
    }

    public void setMax (String max) {
        this.max = max;
    }

    public String getMin () {
        return min;
    }

    public void setMin (String min) {
        this.min = min;
    }

    public long getCount () {
        return count;
    }

    public void setCount (long count) {
        this.count = count;
    }
}
