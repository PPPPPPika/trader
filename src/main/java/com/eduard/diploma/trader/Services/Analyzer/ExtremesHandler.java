package com.eduard.diploma.trader.Services.Analyzer;

import com.eduard.diploma.trader.Models.Candles.Candle;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

public interface ExtremesHandler {
    <CandleType extends Candle> List<? extends Candle> processingExtremes(List<CandleType> listPoints, String key);

    <CandleType extends Candle> Flux<Map<String, List<? extends Candle>>> mergeExtremes(Map<String, List<? extends Candle>> mapCandles);
}
