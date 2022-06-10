package com.eduard.diploma.trader.Services.SpeculatorService.Exceptions;

public class NonExistentMiddleException extends IllegalArgumentException{
    public NonExistentMiddleException(String s) {
        super(s);
    }
}
