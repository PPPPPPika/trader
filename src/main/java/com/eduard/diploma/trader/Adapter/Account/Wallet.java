package com.eduard.diploma.trader.Adapter.Account;

import com.eduard.diploma.trader.Adapter.CryptoCurrencies.Currencies;
import reactor.core.publisher.Mono;

public interface Wallet {
    Mono<String> getCurrency(Currencies currencies);
}
