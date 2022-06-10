package com.eduard.diploma.trader.Adapter.CryptoCurrencies;

public enum CurrenciesPairs {
    BTCBUSD {
        public int getScale(){
            return 5;
        }
    },
    ETHBUSD {
        public int getScale(){
            return 4;
        }
    };
    public abstract int getScale();
}
