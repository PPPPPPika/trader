package com.eduard.diploma.trader.Services.SpeculatorService.AnalysisResultProcessing;

import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import com.eduard.diploma.trader.Services.SpeculatorService.Details.CurrentSituationDetails;
import reactor.core.publisher.Flux;

public interface TradingConditionsResearcher {
    Flux<CurrentSituationDetails> research(CurrenciesPairs currenciesPairs);
}
