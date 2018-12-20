package com.n26.exception;

/**
 * @author richard.dbrsac@gmail.com.
 */
public class NotParsableTransactionException extends RuntimeException {

    public NotParsableTransactionException (String message) {
        super(message);
    }
}
