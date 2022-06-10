package com.eduard.diploma.trader.Services.Analyzer;

import com.eduard.diploma.trader.Models.Candles.Candle;
import com.eduard.diploma.trader.Models.Candles.Enums.KindsCandles;
import reactor.core.publisher.Flux;

import java.util.List;

public interface TrendCandlesExtractor {
    Flux<List<? extends Candle>> extract(List<KindsCandles> kindsCandlesList);
}
