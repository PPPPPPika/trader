package com.eduard.diploma.trader.Services.Receiver;

import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import com.eduard.diploma.trader.Models.Candles.Enums.KindsCandles;
import com.eduard.diploma.trader.Models.Candles.*;
import com.eduard.diploma.trader.Services.Receiver.Exceptions.ReceiveException;
import com.eduard.diploma.trader.Services.Receiver.Exceptions.WrongTimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.*;

@Service
public class ReceiverLaunchServiceImpl implements ReceiverLaunchService {
    private static boolean ACTIVITY_STATUS = false;

    private final ReceiverCandles receiverCandles;
    private final TimeManager timeManager;

    @Autowired
    public ReceiverLaunchServiceImpl(ReceiverCandlesImpl receiverCandles, TimeManager timeManager) {
        this.receiverCandles = receiverCandles;
        this.timeManager = timeManager;
    }

    @Override
    public Flux<PackageCandles> start(CurrenciesPairs currenciesPairs){
        return Flux.just("")
                .flatMap(emptyValue -> {
                    if (!ACTIVITY_STATUS){
                        ACTIVITY_STATUS = true;
                        return getAllPreviousCandles(currenciesPairs);
                    }
                    else
                        return Flux.error(new IllegalArgumentException());
                })
                .onErrorReturn(new PackageCandles())
                .thenMany(
                        timeValidation(currenciesPairs)
                );
    }

    public Flux<PackageCandles> timeValidation(CurrenciesPairs currenciesPairs){
        return Flux.just("")
                .flatMap(emptyValue -> {
                    Calendar calendar = new GregorianCalendar();
                    if (calendar.get(Calendar.SECOND) == 0){
                        List<KindsCandles> listCurrentCandles = new LinkedList<>(List.of(KindsCandles.ONE));
                        listCurrentCandles.addAll(
                                Arrays.stream(timeManager.identifyCurrentCandles(calendar.get(Calendar.MINUTE)))
                                        .filter(Objects::nonNull)
                                        .toList()
                        );
                        listCurrentCandles =
                                listCurrentCandles.stream()
                                        .sorted(Comparator.comparing(KindsCandles::getDurationInterval))
                                        .toList();
                        return getCandles(currenciesPairs, listCurrentCandles);
                    }
                    else
                        return Flux.error(
                                new WrongTimeException(
                                        """
                                        Class: ReceiverLaunchServiceImpl
                                        Method: Flux<Object> start(CurrenciesPairs currenciesPairs)
                                        """
                                )
                        );
                })
                .retryWhen(Retry.fixedDelay(61, Duration.ofMillis(1000)))
                .defaultIfEmpty(new PackageCandles());
    }

    public Flux<PackageCandles> getCandles(CurrenciesPairs currenciesPairs, List<KindsCandles> list){
        if (list.size() == 1)
            return receiverCandles.receive(currenciesPairs, list.get(0))
                    .map(candle -> new PackageCandles(candle));
        else if (list.size() == 2)
            return Flux.zip(receiverCandles.receive(currenciesPairs, list.get(0)),
                            receiverCandles.receive(currenciesPairs, list.get(1)))
                    .map(candles -> new PackageCandles(candles.getT1(), candles.getT2()));
        else if (list.size() == 4)
            return Flux.zip(receiverCandles.receive(currenciesPairs, list.get(0)),
                            receiverCandles.receive(currenciesPairs, list.get(1)),
                            receiverCandles.receive(currenciesPairs, list.get(2)),
                            receiverCandles.receive(currenciesPairs, list.get(3)))
                    .map(candles -> new PackageCandles(candles.getT1(), candles.getT2(), candles.getT3(), candles.getT4()));
        else if (list.size() == 5)
            return Flux.zip(receiverCandles.receive(currenciesPairs, list.get(0)),
                            receiverCandles.receive(currenciesPairs, list.get(1)),
                            receiverCandles.receive(currenciesPairs, list.get(2)),
                            receiverCandles.receive(currenciesPairs, list.get(3)),
                            receiverCandles.receive(currenciesPairs, list.get(4)))
                    .map(candles -> new PackageCandles(candles.getT1(), candles.getT2(), candles.getT3(), candles.getT4(), candles.getT5()));
        else if (list.size() == 6)
            return Flux.zip(receiverCandles.receive(currenciesPairs, list.get(0)),
                            receiverCandles.receive(currenciesPairs, list.get(1)),
                            receiverCandles.receive(currenciesPairs, list.get(2)),
                            receiverCandles.receive(currenciesPairs, list.get(3)),
                            receiverCandles.receive(currenciesPairs, list.get(4)),
                            receiverCandles.receive(currenciesPairs, list.get(5)))
                    .map(candles -> new PackageCandles(candles.getT1(), candles.getT2(), candles.getT3(),
                                                       candles.getT4(), candles.getT5(), candles.getT6()));
        else
            throw new ReceiveException(
                    """
                    Class: ReceiverLaunchServiceImpl
                    Method: Flux<Object> getCandles(CurrenciesPairs currenciesPairs, List<KindsCandles> list)
                    """
            );
    }

    private Flux<PackageCandles> getAllPreviousCandles(CurrenciesPairs currenciesPairs){
        return Flux.zip(receiverCandles.receivePreviousCandles(currenciesPairs, KindsCandles.ONE),
                        receiverCandles.receivePreviousCandles(currenciesPairs, KindsCandles.THREE),
                        receiverCandles.receivePreviousCandles(currenciesPairs, KindsCandles.FIVE),
                        receiverCandles.receivePreviousCandles(currenciesPairs, KindsCandles.FIFTEEN),
                        receiverCandles.receivePreviousCandles(currenciesPairs, KindsCandles.THIRTY),
                        receiverCandles.receivePreviousCandles(currenciesPairs, KindsCandles.SIXTY))
                .map(candles -> new PackageCandles((OneMinuteCandle) candles.getT1(), (ThreeMinuteCandle) candles.getT2(), (FiveMinuteCandle) candles.getT3(),
                        (FifteenMinuteCandle) candles.getT4(), (ThirtyMinuteCandle) candles.getT5(), (SixtyMinuteCandle) candles.getT6())
                );
    }
}
