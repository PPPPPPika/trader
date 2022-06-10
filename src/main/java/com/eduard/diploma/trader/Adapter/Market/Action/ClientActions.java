package com.eduard.diploma.trader.Adapter.Market.Action;

import com.binance.api.client.domain.OrderSide;
import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import reactor.core.publisher.Mono;

public interface ClientActions {
    Mono<String> createOrderTest(CurrenciesPairs currencyPair, OrderSide orderSide, String quantity);
    Mono<String> createOrder(CurrenciesPairs currenciesPairs, OrderSide orderSide, String quantity) throws ClientActionException;
}
