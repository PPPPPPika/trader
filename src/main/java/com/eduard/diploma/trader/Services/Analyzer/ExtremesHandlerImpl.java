package com.eduard.diploma.trader.Services.Analyzer;

import com.eduard.diploma.trader.Models.Candles.Candle;
import com.eduard.diploma.trader.Services.CandlesProcessingService;
import com.eduard.diploma.trader.Services.CandlesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class ExtremesHandlerImpl implements ExtremesHandler {
    private final CandlesProcessingService candlesService;

    @Autowired
    public ExtremesHandlerImpl(CandlesServiceImpl candlesServiceImpl) {
        this.candlesService = candlesServiceImpl;
    }

    @Override
    public <CandleType extends Candle> List<? extends Candle> processingExtremes(List<CandleType> listPoints, String key){
        List<CandleType> updatedPointsList = new LinkedList<>(List.of(listPoints.get(0)));
        long interval = candlesService.determineTypeCandles(listPoints).getDurationInterval() * 1000L;

        List<CandleType> bufferList = new LinkedList<>();
        int listSize = listPoints.size();
        for (int i = 1; i < listSize; i++){
            CandleType currentCandle = listPoints.get(i);
            CandleType previousCandle = listPoints.get(i - 1);

            if (previousCandle.getCloseTime() + interval != currentCandle.getCloseTime())
                updatedPointsList.add(currentCandle);
            else {
                bufferList.add(previousCandle);
                bufferList.add(currentCandle);

                if (i + 1 != listSize){
                    for (int j = i; j < listSize - 1; j++){
                        CandleType currentElement = listPoints.get(j);
                        CandleType nextElement = listPoints.get(j + 1);

                        if (currentElement.getCloseTime() + interval == nextElement.getCloseTime()){
                            bufferList.add(nextElement);
                            if (j + 1 != listSize)
                                i = j + 1;
                            else
                                i = j;
                        }
                        else
                            break;
                    }
                }

                if (key.equals("Maximums"))
                    updatedPointsList.add(bufferList.stream().max(Comparator.comparing(value -> Double.parseDouble(value.getMaxPrice()))).get());
                else if (key.equals("Minimums"))
                    updatedPointsList.add(bufferList.stream().min(Comparator.comparing(value -> Double.parseDouble(value.getMinPrice()))).get());

                bufferList.clear();
            }
        }
        return updatedPointsList;
    }

    @Override
    public <CandleType extends Candle> Flux<Map<String, List<? extends Candle>>> mergeExtremes(Map<String, List<? extends Candle>> mapCandles){
        return Flux.just(mapCandles)
                .map(map -> {
                    List<CandleType> maximumsTrend = (List<CandleType>) mapCandles.get("Maximums").stream().skip(1).toList();
                    List<CandleType> minimumsTrend = (List<CandleType>) mapCandles.get("Minimums");

                    List<CandleType> sequence = new LinkedList<>();
                    List<CandleType> freeMinimums = new LinkedList<>();

                    int minimumsTrendSize = minimumsTrend.size();
                    for (int i = 0; i < minimumsTrendSize - 1; i++){
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
                    map.put("Sequence", sequence);
                    return map;
                });
    }


}
