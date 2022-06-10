package com.eduard.diploma.trader.Services.Receiver.Exceptions;

public class WrongTimeException extends IllegalArgumentException{
    public WrongTimeException(String s) {
        super(s);
    }
}
