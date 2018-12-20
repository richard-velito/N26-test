package com.n26.exception;

/**
 * @author richard.dbrsac@gmail.com
 */
public class OlderTransactionException extends RuntimeException {

    public OlderTransactionException (String message) {
        super(message);
    }
}
