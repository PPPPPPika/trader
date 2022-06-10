package com.eduard.diploma.trader.Services.SpeculatorService.AnalysisResultProcessing;

import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import com.eduard.diploma.trader.Models.Candles.Candle;
import com.eduard.diploma.trader.Models.Candles.Enums.KindsCandles;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

public interface ExplorerExtremes {
    Flux<Map<KindsCandles, List<? extends Candle>>> reviewExtremes(CurrenciesPairs currenciesPairs);
}
