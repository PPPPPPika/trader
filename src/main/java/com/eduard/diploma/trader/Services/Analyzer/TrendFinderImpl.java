package com.eduard.diploma.trader.Services.Analyzer;

import com.eduard.diploma.trader.Models.Candles.Candle;
import com.eduard.diploma.trader.Services.CandlesProcessingService;
import com.eduard.diploma.trader.Services.CandlesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class TrendFinderImpl implements TrendFinder {
    private final CandlesProcessingService candlesService;

    @Autowired
    public TrendFinderImpl(CandlesServiceImpl candlesServiceImpl) {
        this.candlesService = candlesServiceImpl;
    }

    @Override
    public Map<String, List<? extends Candle>> search(List<? extends Candle> candlesList){
        Map<String, List<? extends Candle>> mapUpTrend = searchUpTrend(candlesList);
        Map<String, List<? extends Candle>> mapDownTrend = searchDownTrend(candlesList);

        if (calculationTrendSing(mapUpTrend.get("AllUpTrend")) > 0)
            return mapUpTrend;
        else if (calculationTrendSing(mapDownTrend.get("AllDownTrend")) < 0)
            return mapDownTrend;
        else
            return new HashMap<>();
    }

    private double calculationTrendSing(List<? extends Candle> candlesList){
        Candle firstCandle = candlesList.get(0);
        int lastIndex = candlesList.size() - 1;
        if (candlesService.isPositiveCandle(firstCandle))
            return new BigDecimal(candlesList.get(lastIndex).getClosePrice())
                    .subtract(new BigDecimal(firstCandle.getOpenPrice()))
                    .doubleValue();
        else
            return new BigDecimal(candlesList.get(lastIndex).getClosePrice())
                    .subtract(new BigDecimal(firstCandle.getClosePrice()))
                    .doubleValue();
    }

    private <CandleType extends Candle> HashMap<String, List<? extends Candle>> searchUpTrend(List<CandleType> candlesList){
        List<CandleType> minimumsList = new LinkedList<>(List.of(candlesList.get(candlesList.size() - 1)));
        CandleType currentMinimumCandle = candlesList.get(candlesList.size() - 1);

        int sizeCandlesList = candlesList.size();
        int borderIndex = 0;
        for (int i = sizeCandlesList - 2; i > 0; i--){
            CandleType currentCandle = candlesList.get(i);
            if (Double.parseDouble(currentCandle.getMinPrice()) < Double.parseDouble(currentMinimumCandle.getMinPrice())){
                currentMinimumCandle = currentCandle;
                minimumsList.add(currentMinimumCandle);
                borderIndex = i;
            }
        }
        Collections.reverse(minimumsList);

        CandleType borderCandle =  candlesList.get(borderIndex);
        List<CandleType> maximumsList = new LinkedList<>(List.of(borderCandle));
        CandleType currentMaximumCandle = borderCandle;

        for (int i = borderIndex + 1; i < sizeCandlesList; i++){
            CandleType currentCandle = candlesList.get(i);
            if (Double.parseDouble(currentCandle.getMaxPrice()) > Double.parseDouble(currentMaximumCandle.getMaxPrice())){
                currentMaximumCandle = currentCandle;
                maximumsList.add(currentMaximumCandle);
            }
        }
        return new HashMap<>(Map.of("Minimums", minimumsList, "AllUpTrend", candlesList.subList(borderIndex, candlesList.size() - 1), "Maximums", maximumsList));
    }

    private <CandleType extends Candle> HashMap<String, List<? extends Candle>> searchDownTrend(List<CandleType> candlesList){
        int penultimateCandleIndex = candlesList.size() - 1;
        CandleType penultimateCandle = candlesList.get(penultimateCandleIndex);

        List<CandleType> maximumsList = new ArrayList<>(List.of(penultimateCandle));
        CandleType currentMaximum = penultimateCandle;
        int indexCurrentMaximum = 0;

        for (int i = candlesList.size() - 2; i > 0; i--){
            CandleType currentCandle = candlesList.get(i);
            if (Double.parseDouble(currentCandle.getMaxPrice()) > Double.parseDouble(currentMaximum.getMaxPrice())){
                currentMaximum = currentCandle;
                maximumsList.add(currentCandle);
                indexCurrentMaximum = i;
            }
        }

        List<CandleType> minimumList = new LinkedList<>(List.of(candlesList.get(indexCurrentMaximum)));
        CandleType currentMinimumCandle = candlesList.get(indexCurrentMaximum);

        for (int i = indexCurrentMaximum + 1; i < candlesList.size() - 1; i++){
            CandleType currentCandle = candlesList.get(i);
            if (Double.parseDouble(currentCandle.getMinPrice()) < Double.parseDouble(currentMinimumCandle.getMinPrice())){
                currentMinimumCandle = currentCandle;
                minimumList.add(currentMinimumCandle);
            }
        }
        return new HashMap<>(
                Map.of("Maximums", maximumsList, "Minimums", minimumList, "AllDownTrend", candlesList.subList(indexCurrentMaximum, candlesList.size() - 1))
        );
    }
}
