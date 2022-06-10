package com.eduard.diploma.trader.Services.SpeculatorService.Exceptions;

public class WaitException extends IllegalArgumentException{
    public WaitException(String s) {
        super(s);
    }
}
