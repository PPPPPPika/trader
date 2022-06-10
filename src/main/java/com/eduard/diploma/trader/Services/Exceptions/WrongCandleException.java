package com.eduard.diploma.trader.Services.Exceptions;

public class WrongCandleException extends IllegalArgumentException{
    public WrongCandleException(String s) {
        super(s);
    }
}
