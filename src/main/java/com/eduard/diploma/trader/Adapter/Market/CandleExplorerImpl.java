package com.eduard.diploma.trader.Adapter.Market;

import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.eduard.diploma.trader.Adapter.Configuration.ClientConfiguration;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
public class CandleExplorerImpl implements CandleExplorer {
    @Override
    public Flux<List<Candlestick>> getBinanceCandle_1(Long endTime) {
        return Flux.just(
                new ClientConfiguration()
                        .getClient()
                        .getCandlestickBars("BTCBUSD", CandlestickInterval.ONE_MINUTE, 90, endTime - 5400000, endTime)
        );
    }

    @Override
    public Flux<List<Candlestick>> getBinanceCandle_3(Long endTime) {
        return Flux.just(
                new ClientConfiguration()
                        .getClient()
                        .getCandlestickBars("BTCBUSD", CandlestickInterval.THREE_MINUTES, 90, endTime - 16200000, endTime)
        );
    }

    @Override
    public Flux<List<Candlestick>> getBinanceCandle_5(Long endTime) {
        return Flux.just(
                new ClientConfiguration()
                        .getClient()
                        .getCandlestickBars("BTCBUSD", CandlestickInterval.FIVE_MINUTES, 90, endTime - 27000000, endTime)
        );
    }

    @Override
    public Flux<List<Candlestick>> getBinanceCandle_15(Long endTime) {
        return Flux.just(
                new ClientConfiguration()
                        .getClient()
                        .getCandlestickBars("BTCBUSD", CandlestickInterval.FIFTEEN_MINUTES, 90, endTime - 81000000, endTime)
        );
    }

    @Override
    public Flux<List<Candlestick>> getBinanceCandle_30(Long endTime) {
        return Flux.just(
                new ClientConfiguration()
                        .getClient()
                        .getCandlestickBars("BTCBUSD", CandlestickInterval.HALF_HOURLY, 90, endTime - 162000000, endTime)
        );
    }

    @Override
    public Flux<List<Candlestick>> getBinanceCandle_60(Long endTime) {
        return Flux.just(
                new ClientConfiguration()
                        .getClient()
                        .getCandlestickBars("BTCBUSD", CandlestickInterval.HOURLY, 90, endTime - 324000000, endTime)
        );
    }
}
