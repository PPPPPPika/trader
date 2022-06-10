package com.eduard.diploma.trader.Services.SpeculatorService.AnalysisResultProcessing;

import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import com.eduard.diploma.trader.Models.Candles.Candle;
import com.eduard.diploma.trader.Models.Candles.Enums.KindsCandles;
import com.eduard.diploma.trader.Services.SpeculatorService.Details.TrendDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RangeCalculatorImpl implements RangeCalculator{
    private final ExplorerExtremesImpl explorerExtremesImpl;

    @Autowired
    public RangeCalculatorImpl(ExplorerExtremesImpl explorerExtremesImpl) {
        this.explorerExtremesImpl = explorerExtremesImpl;
    }

    @Override
    public Flux<TrendDetails> build(CurrenciesPairs currenciesPairs){
        return Flux.just("")
                .flatMap(emptyValue -> explorerExtremesImpl.reviewExtremes(currenciesPairs))
                .filter(map -> {
                    if (map.keySet().size() == 1){
                        return correctnessLastExtreme(map.get(map.keySet().stream().limit(1).toList().get(0)));
                    }
                    else
                        return false;
                })
                .map(map -> {
                    KindsCandles kindCandle = map.keySet().stream().limit(1).toList().get(0);
                    return new TrendDetails(kindCandle, map.get(kindCandle), computeMiddleRanges(map));
                });
    }

    private Map<String, String> computeMiddleRanges(Map<KindsCandles, List<? extends Candle>> map){
        Map<String, String> middlesMap = new HashMap<>();
        for (Map.Entry<KindsCandles, List<? extends Candle>> currentMap : map.entrySet()){
            int listSize = currentMap.getValue().size();
            int index = 0;
            for (int i = listSize - 4; i < listSize; i+=2){
                String middle =
                        new BigDecimal(currentMap.getValue().get(i + 1).getMaxPrice())
                                .subtract(new BigDecimal(currentMap.getValue().get(i).getMinPrice()))
                                .divide(new BigDecimal("2"), 2, RoundingMode.DOWN)
                                .add(new BigDecimal(currentMap.getValue().get(i).getMinPrice()))
                                .toString();
                if (index == 0)
                    middlesMap.put("firstMiddle", middle);
                else
                    middlesMap.put("secondMiddle", middle);

                index++;
            }
        }
        return middlesMap;
    }

    private boolean correctnessLastExtreme(List<? extends Candle> extremesList){
        Candle lastCandle = extremesList.get(extremesList.size() - 1);
        int sizeExtremesList = extremesList.size();
        for (int i = 0; i < sizeExtremesList - 1; i++){
            if (lastCandle.getCloseTime() > extremesList.get(i).getCloseTime()
                    && Double.parseDouble(lastCandle.getMaxPrice()) > Double.parseDouble(extremesList.get(i).getMaxPrice()))
                return true;
        }
        return false;
    }

}
