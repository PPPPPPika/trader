package com.eduard.diploma.trader.Models.Candles;

import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;

public abstract class Candle {
    public abstract Long getId();
    public abstract void setId(Long id);

    public abstract CurrenciesPairs getCurrencyPair();
    public abstract void setCurrencyPair(CurrenciesPairs currencyPair);

    public abstract Long getOpenTime();
    public abstract void setOpenTime(Long openTime);

    public abstract String getOpenTimeMos();
    public abstract void setOpenTimeMos(String openTimeMos);

    public abstract String getOpenPrice();
    public abstract void setOpenPrice(String openPrice);

    public abstract Long getCloseTime();
    public abstract void setCloseTime(Long closeTime);

    public abstract String getCloseTimeMos();
    public abstract void setCloseTimeMos(String closeTimeMos);

    public abstract String getClosePrice();
    public abstract void setClosePrice(String closePrice);

    public abstract String getMaxPrice();
    public abstract void setMaxPrice(String maxPrice);

    public abstract String getMinPrice();
    public abstract void setMinPrice(String minPrice);

}
