package com.eduard.diploma.trader.Services.SpeculatorService.AnalysisResultProcessing;

import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import com.eduard.diploma.trader.Models.Candles.Candle;
import com.eduard.diploma.trader.Models.Candles.Enums.KindsCandles;
import com.eduard.diploma.trader.Services.Analyzer.TrendAnalyzer;
import com.eduard.diploma.trader.Services.Analyzer.TrendAnalyzerImpl;
import com.eduard.diploma.trader.Services.CandlesService;
import com.eduard.diploma.trader.Services.CandlesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class ExplorerExtremesImpl implements ExplorerExtremes{
    private final TrendAnalyzer trendAnalyzer;
    private final CandlesService candlesService;

    @Autowired
    public ExplorerExtremesImpl(TrendAnalyzerImpl trendAnalyzerImpl,
                                CandlesServiceImpl candlesServiceImpl) {
        this.trendAnalyzer = trendAnalyzerImpl;
        this.candlesService = candlesServiceImpl;
    }

    @Override
    public Flux<Map<KindsCandles, List<? extends Candle>>> reviewExtremes(CurrenciesPairs currenciesPairs){
        return Flux.just("")
                /*.map(val -> {
                    System.out.println("Start reviewExtremes");
                    return val;
                })*/
                .flatMap(emptyValue -> trendAnalyzer.analyzing(currenciesPairs))
                .filterWhen(map -> {
                    KindsCandles kindCandle = map.keySet().stream().toList().get(0);
                    return getLastCandles(kindCandle)
                            .map(lastCandles -> validateClosingTime(map.get(kindCandle), lastCandles));
                })
                .map(val -> {
                    System.out.println("Extremes: " + val);
                    return val;
                });
    }

    private boolean validateClosingTime(List<? extends Candle> currentList, List<? extends Candle> lastCandlesList){
        int sizeLastCandlesList = lastCandlesList.size();
        for (int i = 0; i < sizeLastCandlesList; i++){
            if (currentList.get(currentList.size() - 1).getCloseTime().equals(lastCandlesList.get(i).getCloseTime()))
                return true;
        }
        return false;
    }

    private Mono<List<? extends Candle>> getLastCandles(KindsCandles kindsCandles){
        return Mono.just("")
                .map(emptyValue -> candlesService.findLastThreeCandles(kindsCandles))
                .flatMap(Flux::collectList)
                /*.map(val -> {
                    System.out.println("Last candles: " + val);
                    return val;
                })*/;
    }

}
