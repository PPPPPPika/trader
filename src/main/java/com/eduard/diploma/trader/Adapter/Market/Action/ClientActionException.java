package com.eduard.diploma.trader.Adapter.Market.Action;

import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;

public class ClientActionException extends Exception{
    private final CurrenciesPairs currenciesPairs;

    public ClientActionException(String exceptionMessage, CurrenciesPairs currenciesPairs){
        super(exceptionMessage);
        this.currenciesPairs = currenciesPairs;
    }

    @Override
    public String toString() {
        return "ClientActionException{" +
                "message=" + getMessage() +
                "currencyPairs=" + currenciesPairs.toString() +
                '}';
    }
}
