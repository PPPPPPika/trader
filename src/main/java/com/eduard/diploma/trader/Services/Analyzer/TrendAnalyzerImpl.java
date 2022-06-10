package com.eduard.diploma.trader.Services.Analyzer;

import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import com.eduard.diploma.trader.Models.Candles.Candle;
import com.eduard.diploma.trader.Models.Candles.Enums.KindsCandles;
import com.eduard.diploma.trader.Services.CandlesServiceImpl;
import com.eduard.diploma.trader.Services.Receiver.ReceiverLaunchService;
import com.eduard.diploma.trader.Services.Receiver.ReceiverLaunchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrendAnalyzerImpl implements TrendAnalyzer {
    private final ReceiverLaunchService receiverLaunchService;
    private final CandlesServiceImpl candlesService;
    private final TrendFinder trendFinder;
    private final ExtremesHandler extremesHandler;
    private final TrendCandlesExtractor trendCandlesExtractor;

    @Autowired
    public TrendAnalyzerImpl(ReceiverLaunchServiceImpl receiverLaunchServiceImpl,
                             CandlesServiceImpl candlesServiceImpl,
                             TrendFinderImpl trendFinderImpl,
                             ExtremesHandlerImpl extremesHandlerImpl,
                             TrendCandlesExtractorImpl trendCandlesExtractorImpl){
        this.receiverLaunchService = receiverLaunchServiceImpl;
        this.candlesService = candlesServiceImpl;
        this.trendFinder = trendFinderImpl;
        this.extremesHandler = extremesHandlerImpl;
        this.trendCandlesExtractor = trendCandlesExtractorImpl;
    }

    /*public Flux<Map<KindsCandles, List<? extends Candle>>> start(CurrenciesPairs currenciesPairs){
        return startAnalyze(currenciesPairs)
                .doOnNext(map -> map.forEach((key, value) -> System.out.println("\n" + "Key: " + key + ";\n" + "Value: " + value + ";\n")))
                .repeat(60);
    }*/

    @Override
    public Flux<Map<KindsCandles, List<? extends Candle>>> analyzing(CurrenciesPairs currenciesPairs){
        return Flux.just("")
                .map(val -> {
                    System.out.println("Start analyze!");
                    return val;
                })
                .flatMap(emptyValue -> determineTrend(currenciesPairs))
                .filter(map -> map.containsKey("AllUpTrend") && limitingExtremes(map))
                .map(map -> {
                    for (Map.Entry<String, List<? extends Candle>> mapCurrent : map.entrySet()){
                        if (mapCurrent.getKey().equals("Maximums"))
                            map.replace("Maximums", extremesHandler.processingExtremes(mapCurrent.getValue(), mapCurrent.getKey()));
                        else if (mapCurrent.getKey().equals("Minimums"))
                            map.replace("Minimums", extremesHandler.processingExtremes(mapCurrent.getValue(), mapCurrent.getKey()));
                    }
                    return map;
                })
                .flatMap(this::removeUnnecessary)
                .filter(this::limitingExtremes)
                .flatMap(extremesHandler::mergeExtremes)
                .filter(value -> value.get("Sequence").size() >= 4)
                .map(val -> {
                    System.out.println("Finish analyze!");
                    return val;
                })
                .map(map -> new HashMap<>(Map.of(candlesService.determineTypeCandles(map.get("Sequence")), map.get("Sequence"))));
    }

    private Flux<Map<String, List<? extends Candle>>> removeUnnecessary(Map<String, List<? extends Candle>> trendMap){
        return Flux.just("")
                .map(emptyValue -> trendMap)
                .map(map -> {
                    for (Map.Entry<String, List<? extends Candle>> mapCurrent : map.entrySet()){
                        if (mapCurrent.getKey().equals("Maximums") || mapCurrent.getKey().equals("Minimums")){
                            for (int i = 1; i < mapCurrent.getValue().size(); i++){
                                if (mapCurrent.getValue().get(i).getCloseTime().equals(mapCurrent.getValue().get(i - 1).getCloseTime()))
                                    mapCurrent.getValue().remove(i);
                            }
                        }
                    }
                    return map;
                });
    }

    private Flux<Map<String, List<? extends Candle>>> determineTrend(CurrenciesPairs currenciesPairs){
        return Flux.just("")
                .flatMap(emptyValue -> receiverLaunchService.start(currenciesPairs))
                .flatMap(packageCandles ->
                        trendCandlesExtractor.extract(candlesService.unpackCandlesPackage(packageCandles))
                                .doOnNext(value -> System.out.println("Extract: " + value.get(0).getClass()))
                                .map(trendFinder::search)
                );
    }

    private boolean limitingExtremes(Map<String, List<? extends Candle>> map){
        return map.get("Minimums").size() >= 2 && map.get("Maximums").size() >= 2;
    }
}
