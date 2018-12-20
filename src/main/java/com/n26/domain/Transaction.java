package com.n26.domain;

import java.math.BigDecimal;

/**
 * @author richard.dbrsac@gmail.com
 */

public class Transaction {

    private BigDecimal amount;
    private long timestamp;

    public BigDecimal getAmount () {
        return amount;
    }

    public void setAmount (BigDecimal amount) {
        this.amount = amount;
    }

    public long getTimestamp () {
        return timestamp;
    }

    public void setTimestamp (long timestamp) {
        this.timestamp = timestamp;
    }
}
