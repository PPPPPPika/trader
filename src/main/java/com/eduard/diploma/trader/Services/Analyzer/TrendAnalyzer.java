package com.eduard.diploma.trader.Services.Analyzer;

import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import com.eduard.diploma.trader.Models.Candles.Candle;
import com.eduard.diploma.trader.Models.Candles.Enums.KindsCandles;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

public interface TrendAnalyzer {
    Flux<Map<KindsCandles, List<? extends Candle>>> analyzing(CurrenciesPairs currenciesPairs);
}
