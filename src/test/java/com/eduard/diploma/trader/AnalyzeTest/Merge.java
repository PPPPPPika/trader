package com.eduard.diploma.trader.AnalyzeTest;

import com.eduard.diploma.trader.Models.Candles.Candle;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Merge {


    public <CandleType extends Candle> Map<String, List<? extends Candle>> mergeExtremesV2(Map<String, List<? extends Candle>> mapCandles){
        List<CandleType> maximumsTrend = (List<CandleType>) mapCandles.get("Maximums").stream().skip(1).toList();
        List<CandleType> minimumsTrend = (List<CandleType>) mapCandles.get("Minimums");

        List<CandleType> sequence = new LinkedList<>();
        List<CandleType> freeMinimums = new LinkedList<>();

        for (int i = 0; i < minimumsTrend.size() - 1; i++){
            int indexMinimum = i;
            List<CandleType> maximums =
                    maximumsTrend.stream()
                            .filter(maximum -> maximum.getCloseTime() > minimumsTrend.get(indexMinimum).getCloseTime()
                                    && maximum.getCloseTime() < minimumsTrend.get(indexMinimum + 1).getCloseTime())
                            .toList();

            if (!maximums.isEmpty()){
                if (freeMinimums.isEmpty()){
                    sequence.add(minimumsTrend.get(i));
                    sequence.add(maximums.stream().max(Comparator.comparing(value -> Double.parseDouble(value.getMaxPrice()))).get());
                }
                else {
                    sequence.add(freeMinimums.stream().min(Comparator.comparing(value -> Double.parseDouble(value.getMinPrice()))).get());
                    sequence.add(maximums.stream().max(Comparator.comparing(value -> Double.parseDouble(value.getMaxPrice()))).get());
                    freeMinimums.clear();
                }
            }
            else {
                freeMinimums.add(minimumsTrend.get(i));
            }
        }
        mapCandles.put("Sequence", sequence);

        return mapCandles;
    }



}
