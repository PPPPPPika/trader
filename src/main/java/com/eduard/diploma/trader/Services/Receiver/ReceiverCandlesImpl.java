package com.eduard.diploma.trader.Services.Receiver;

import com.binance.api.client.domain.market.Candlestick;
import com.eduard.diploma.trader.Adapter.Configuration.ClientConfiguration;
import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import com.eduard.diploma.trader.Adapter.Market.CandleExplorer;
import com.eduard.diploma.trader.Adapter.Market.CandleExplorerImpl;
import com.eduard.diploma.trader.Models.Candles.*;
import com.eduard.diploma.trader.Models.Candles.Enums.KindsCandles;
import com.eduard.diploma.trader.Services.CandlesServiceImpl;
import com.eduard.diploma.trader.Services.Receiver.Exceptions.DuplicateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@Component
public class ReceiverCandlesImpl implements ReceiverCandles{
    private final CandlesServiceImpl candlesService;
    private final CandleExplorer candleExplorer;
    private final ClientConfiguration clientConfiguration;

    @Autowired
    public ReceiverCandlesImpl(CandlesServiceImpl candlesService, CandleExplorerImpl candleExplorerImpl, ClientConfiguration clientConfiguration) {
        this.candlesService = candlesService;
        this.candleExplorer = candleExplorerImpl;
        this.clientConfiguration = clientConfiguration;
    }

    @Override
    public Flux<? extends Candle> receivePreviousCandles(CurrenciesPairs currenciesPairs, KindsCandles kindsCandles){
        return switch (kindsCandles) {
            case ONE ->
                    candleExplorer.getBinanceCandle_1(new Date().getTime())
                            .map(candlestickList -> candlestickList.stream()
                                    .map(candlestick -> new OneMinuteCandle(null, currenciesPairs,
                                                    candlestick.getOpenTime(), candlesService.processingDate(candlestick.getOpenTime()), candlestick.getOpen(),
                                                    candlestick.getCloseTime(), candlesService.processingDate(candlestick.getCloseTime()), candlestick.getClose(),
                                                    candlestick.getHigh(), candlestick.getLow())
                                    )
                                    .toList()
                            )
                            .map(candles -> candles.stream().limit(candles.size() - 1).toList())
                            .flatMap(candlesService::saveManyCandles);
            case THREE ->
                    candleExplorer.getBinanceCandle_3(new Date().getTime())
                            .map(candlestickList -> candlestickList.stream()
                                    .map(candlestick -> new ThreeMinuteCandle(null, currenciesPairs,
                                            candlestick.getOpenTime(), candlesService.processingDate(candlestick.getOpenTime()), candlestick.getOpen(),
                                            candlestick.getCloseTime(), candlesService.processingDate(candlestick.getCloseTime()), candlestick.getClose(),
                                            candlestick.getHigh(), candlestick.getLow())
                                    )
                                    .toList()
                            )
                            .map(candles -> candles.stream().limit(candles.size() - 1).toList())
                            .flatMap(candlesService::saveManyCandles);
            case FIVE ->
                    candleExplorer.getBinanceCandle_5(new Date().getTime())
                            .map(candlestickList -> candlestickList.stream()
                                    .map(candlestick -> new FiveMinuteCandle(null, currenciesPairs,
                                            candlestick.getOpenTime(), candlesService.processingDate(candlestick.getOpenTime()), candlestick.getOpen(),
                                            candlestick.getCloseTime(), candlesService.processingDate(candlestick.getCloseTime()), candlestick.getClose(),
                                            candlestick.getHigh(), candlestick.getLow())
                                    )
                                    .toList()
                            )
                            .map(candles -> candles.stream().limit(candles.size() - 1).toList())
                            .flatMap(candlesService::saveManyCandles);
            case FIFTEEN ->
                    candleExplorer.getBinanceCandle_15(new Date().getTime())
                            .map(candlestickList -> candlestickList.stream()
                                    .map(candlestick -> new FifteenMinuteCandle(null, currenciesPairs,
                                            candlestick.getOpenTime(), candlesService.processingDate(candlestick.getOpenTime()), candlestick.getOpen(),
                                            candlestick.getCloseTime(), candlesService.processingDate(candlestick.getCloseTime()), candlestick.getClose(),
                                            candlestick.getHigh(), candlestick.getLow())
                                    )
                                    .toList()
                            )
                            .map(candles -> candles.stream().limit(candles.size() - 1).toList())
                            .flatMap(candlesService::saveManyCandles);
            case THIRTY ->
                    candleExplorer.getBinanceCandle_30(new Date().getTime())
                            .map(candlestickList -> candlestickList.stream()
                                    .map(candlestick -> new ThirtyMinuteCandle(null, currenciesPairs,
                                            candlestick.getOpenTime(), candlesService.processingDate(candlestick.getOpenTime()), candlestick.getOpen(),
                                            candlestick.getCloseTime(), candlesService.processingDate(candlestick.getCloseTime()), candlestick.getClose(),
                                            candlestick.getHigh(), candlestick.getLow())
                                    )
                                    .toList()
                            )
                            .map(candles -> candles.stream().limit(candles.size() - 1).toList())
                            .flatMap(candlesService::saveManyCandles);
            case SIXTY ->
                    candleExplorer.getBinanceCandle_60(new Date().getTime())
                            .map(candlestickList -> candlestickList.stream()
                                    .map(candlestick -> new SixtyMinuteCandle(null, currenciesPairs,
                                            candlestick.getOpenTime(), candlesService.processingDate(candlestick.getOpenTime()), candlestick.getOpen(),
                                            candlestick.getCloseTime(), candlesService.processingDate(candlestick.getCloseTime()), candlestick.getClose(),
                                            candlestick.getHigh(), candlestick.getLow())
                                    )
                                    .toList()
                            )
                            .map(candles -> candles.stream().limit(candles.size() - 1).toList())
                            .flatMap(candlesService::saveManyCandles);
        };
    }

    @Override
    public Flux<Object> receive(CurrenciesPairs currenciesPairs, KindsCandles kindsCandles){
        return Flux.just("")
                .flatMap(value -> getCandle(currenciesPairs, kindsCandles))
                .flatMap(candlesService::saveCandle)
                .cast(Object.class)
                .log()
                .onErrorReturn(new Object());
    }

    private Flux<Object> getCandle(CurrenciesPairs currenciesPairs, KindsCandles kindsCandles){
        return getCandlestick(currenciesPairs, kindsCandles)
                .map(candlestick -> candlesService.selectionCandleType(candlestick, kindsCandles.getCandlestickInterval(), currenciesPairs))
                .flatMap(this::checkDuplicate)
                .retryWhen(Retry.fixedDelay(50, Duration.ofMillis(1000)));
    }
    
    private Flux<Candlestick> getCandlestick(CurrenciesPairs currenciesPairs, KindsCandles kindsCandles){
        return Flux.just("")
                .map(value -> {
                    List<Candlestick> candlestickList = clientConfiguration.getClient()
                            .getCandlestickBars(currenciesPairs.toString(), kindsCandles.getCandlestickInterval());
                    return candlestickList.get(candlestickList.size() - 2);
                });
    }

    private Mono<Object> checkDuplicate(Object candle){
        return candlesService.isDuplicateCandle(candle)
                .map(searchResult -> {
                    if (searchResult){
                        System.out.println(new Date() + " " + "This is duplicate: " + candle + "\n");
                        throw new DuplicateException(
                                """
                                Class: ReceiverCandlesImpl
                                Method: checkDuplicate(Object candle)
                                """
                        );
                    }
                    else
                        return candle;
                });
    }

}
