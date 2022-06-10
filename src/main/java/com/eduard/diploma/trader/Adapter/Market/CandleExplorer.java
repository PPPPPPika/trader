package com.eduard.diploma.trader.Adapter.Market;

import com.binance.api.client.domain.market.Candlestick;
import reactor.core.publisher.Flux;

import java.util.List;

public interface CandleExplorer {

    Flux<List<Candlestick>> getBinanceCandle_1(Long endTime);

    Flux<List<Candlestick>> getBinanceCandle_3(Long endTime);

    Flux<List<Candlestick>> getBinanceCandle_5(Long endTime);

    Flux<List<Candlestick>> getBinanceCandle_15(Long endTime);

    Flux<List<Candlestick>> getBinanceCandle_30(Long endTime);

    Flux<List<Candlestick>> getBinanceCandle_60(Long endTime);
}
