package com.eduard.diploma.trader.Services.Receiver.Exceptions;

public class DuplicateException extends IllegalArgumentException{
    public DuplicateException(String s) {
        super(s);
    }
}
