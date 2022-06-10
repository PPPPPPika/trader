package com.eduard.diploma.trader.Services.Exceptions;

public class WrongCandleIntervalException extends IllegalArgumentException{
    public WrongCandleIntervalException(String s) {
        super(s);
    }
}
