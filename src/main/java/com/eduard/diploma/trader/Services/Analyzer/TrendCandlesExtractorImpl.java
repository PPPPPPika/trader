package com.eduard.diploma.trader.Services.Analyzer;

import com.eduard.diploma.trader.Models.Candles.Candle;
import com.eduard.diploma.trader.Models.Candles.Enums.KindsCandles;
import com.eduard.diploma.trader.Services.CandlesService;
import com.eduard.diploma.trader.Services.CandlesServiceImpl;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Component
public class TrendCandlesExtractorImpl implements TrendCandlesExtractor {
    private final CandlesService candlesService;

    public TrendCandlesExtractorImpl(CandlesServiceImpl candlesServiceImpl) {
        this.candlesService = candlesServiceImpl;
    }

    @Override
    public Flux<List<? extends Candle>> extract(List<KindsCandles> kindsCandlesList){
        return Flux.fromIterable(findAllTrendCandles(kindsCandlesList))
                .flatMap(Flux::collectList)
                .map(list -> list.stream().sorted(Comparator.comparing(Candle::getCloseTime)).toList());
    }

    private List<Flux<? extends Candle>> findAllTrendCandles(List<KindsCandles> kindsCandlesList){
        List<Flux<? extends Candle>> allTrendCandlesList = new LinkedList<>();

        int listSize = kindsCandlesList.size();
        for (int i = 0; i < listSize; i++){
            allTrendCandlesList.add(candlesService.findLastNinetyCandles(kindsCandlesList.get(i)));
        }
        return allTrendCandlesList;
    }

}
