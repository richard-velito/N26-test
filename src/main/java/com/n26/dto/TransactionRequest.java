package com.n26.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author richard.dbrsac@gmail.com
 */

public class TransactionRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String amount;
    private String timestamp;

    public String getAmount () {
        return amount;
    }

    public void setAmount (String amount) {
        this.amount = amount;
    }

    public String getTimestamp () {
        return timestamp;
    }

    public void setTimestamp (String timestamp) {
        this.timestamp = timestamp;
    }
}
