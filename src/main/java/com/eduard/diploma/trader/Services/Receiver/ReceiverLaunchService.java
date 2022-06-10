package com.eduard.diploma.trader.Services.Receiver;

import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import reactor.core.publisher.Flux;

public interface ReceiverLaunchService {
    Flux<PackageCandles> start(CurrenciesPairs currenciesPairs);
}
