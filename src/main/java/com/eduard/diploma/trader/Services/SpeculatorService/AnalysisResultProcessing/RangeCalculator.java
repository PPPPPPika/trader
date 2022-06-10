package com.eduard.diploma.trader.Services.SpeculatorService.AnalysisResultProcessing;

import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import com.eduard.diploma.trader.Services.SpeculatorService.Details.TrendDetails;
import reactor.core.publisher.Flux;

public interface RangeCalculator {
    Flux<TrendDetails> build(CurrenciesPairs currenciesPairs);
}
