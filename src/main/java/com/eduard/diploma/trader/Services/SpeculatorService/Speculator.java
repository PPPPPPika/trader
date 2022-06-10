package com.eduard.diploma.trader.Services.SpeculatorService;

import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import com.eduard.diploma.trader.Services.SpeculatorService.Details.CurrentSituationDetails;
import reactor.core.publisher.Flux;

public interface Speculator {
    Flux<CurrentSituationDetails> trade(CurrenciesPairs currenciesPairs);
}
