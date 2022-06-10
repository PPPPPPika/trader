package com.eduard.diploma.trader.Adapter.Market;

import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import reactor.core.publisher.Mono;

public interface PriceExplorer {
    Mono<String> getValueCurrencyPairs(CurrenciesPairs currenciesPairs);
    //Mono<Candlestick> getMinuteValueCurrencyPairs(CurrenciesPairs currenciesPairs);
    Mono<String> getMinPriceOrder(CurrenciesPairs currenciesPairs);
    Mono<String> getAmountCurrencyForBuy(CurrenciesPairs currenciesPairs, String sumOfBuy);
}
