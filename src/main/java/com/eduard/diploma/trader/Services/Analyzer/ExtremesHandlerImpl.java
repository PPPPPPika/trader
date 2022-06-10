package com.eduard.diploma.trader.Services.Analyzer;

import com.eduard.diploma.trader.Models.Candles.Candle;
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
    private final CandlesServiceImpl candlesService;

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
            if (listPoints.get(i - 1).getCloseTime() + interval != listPoints.get(i).getCloseTime())
                updatedPointsList.add(listPoints.get(i));
            else {
                bufferList.add(listPoints.get(i - 1));
                bufferList.add(listPoints.get(i));

                if (i + 1 != listSize){
                    for (int j = i; j < listSize - 1; j++){
                        if (listPoints.get(j).getCloseTime() + interval == listPoints.get(j + 1).getCloseTime()){
                            bufferList.add(listPoints.get(j + 1));
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
