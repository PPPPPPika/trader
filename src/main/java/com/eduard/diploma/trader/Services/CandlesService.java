package com.eduard.diploma.trader.Services;

import com.eduard.diploma.trader.Models.Candles.Candle;
import com.eduard.diploma.trader.Models.Candles.Enums.KindsCandles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CandlesService {
    <CandleType extends Candle> Flux<? extends Candle> saveManyCandles(List<CandleType> listCandles);

    Mono<?> saveCandle(Object candle);

    Mono<Boolean> isDuplicateCandle(Object candle);

    Mono<? extends Candle> findLastCandle(KindsCandles kindsCandles);

    Flux<? extends Candle> findLastThreeCandles(KindsCandles kindsCandles);

    Flux<? extends Candle> findLastNinetyCandles(KindsCandles kindsCandles);

}
