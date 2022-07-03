package com.eduard.diploma.trader.Services.SpeculatorService.AnalysisResultProcessing;

import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import com.eduard.diploma.trader.Models.Candles.Candle;
import com.eduard.diploma.trader.Services.CandlesService;
import com.eduard.diploma.trader.Services.CandlesServiceImpl;
import com.eduard.diploma.trader.Services.SpeculatorService.Details.CurrentSituationDetails;
import com.eduard.diploma.trader.Services.SpeculatorService.Details.RangeDetails;
import com.eduard.diploma.trader.Services.SpeculatorService.Details.TrendDetails;
import com.eduard.diploma.trader.Services.SpeculatorService.Exceptions.NonExistentMiddleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Component
public class TradingConditionsResearcherImpl implements TradingConditionsResearcher{
    private final RangeCalculator rangeCalculator;
    private final CandlesService candlesService;

    @Autowired
    public TradingConditionsResearcherImpl(RangeCalculatorImpl rangeCalculator, CandlesServiceImpl candlesServiceImpl) {
        this.rangeCalculator = rangeCalculator;
        this.candlesService = candlesServiceImpl;
    }

    @Override
    public Flux<CurrentSituationDetails> research(CurrenciesPairs currenciesPairs){
        return rangeCalculator.build(currenciesPairs)
                .flatMap(trendDetails ->
                        candlesService.findLastCandle(trendDetails.getKindCandle())
                            .map(currentCandle -> {
                                RangeDetails rangeDetails = null;
                                String finalMiddle = "";

                                for (Map.Entry<String, String> currenMap : trendDetails.getMiddles().entrySet()){
                                    if (currenMap.getKey().equals("firstMiddle")){
                                        List<? extends Candle> listExtremes = trendDetails.getExtremes();
                                        BigDecimal radiusRange =
                                                new BigDecimal(listExtremes.get(trendDetails.getExtremes().size() - 3).getMaxPrice())
                                                        .subtract(new BigDecimal(listExtremes.get(trendDetails.getExtremes().size() - 4).getMinPrice()))
                                                        .divide(new BigDecimal("4"), 2, RoundingMode.DOWN)
                                                        .divide(new BigDecimal("2"), 2, RoundingMode.DOWN);

                                        double highBorder = new BigDecimal(currenMap.getValue()).add(radiusRange).doubleValue();
                                        double lowBorder = new BigDecimal(currenMap.getValue()).subtract(radiusRange).doubleValue();

                                        double startLastImpulse = Double.parseDouble(listExtremes.get(listExtremes.size() - 2).getMinPrice());
                                        if (startLastImpulse < highBorder && startLastImpulse > lowBorder){
                                            finalMiddle = trendDetails.getMiddles().get("secondMiddle");
                                            rangeDetails = computeRangeDetails(trendDetails);
                                        }
                                        else {
                                            finalMiddle = computeMiddleGeneralRange(trendDetails);
                                            rangeDetails = computeRangeDetailsFromGeneralRange(trendDetails);
                                        }
                                    }
                                }
                                if (rangeDetails == null)
                                    throw new NonExistentMiddleException("First middle do not exist!");
                                else {
                                    rangeDetails.getMiddles().put("finalMiddle", finalMiddle);
                                    return new CurrentSituationDetails(rangeDetails, currentCandle);
                                }
                            })
                            .defaultIfEmpty(new CurrentSituationDetails())
                            .filter(currentSituationDetails -> !currentSituationDetails.isEmpty())
                            .filter(currentSituationDetails -> {
                                double currentMaximum =
                                        Double.parseDouble(currentSituationDetails.getExtremes().get(currentSituationDetails.getExtremes().size() - 1).getMaxPrice());
                                return Double.parseDouble(currentSituationDetails.getCurrentCandle().getMaxPrice()) < currentMaximum;
                            })
                );
    }

    private RangeDetails computeRangeDetails(TrendDetails trendDetails){
        List<? extends Candle> listExtremes = trendDetails.getExtremes();
        BigDecimal radiusSecondRange =
                new BigDecimal(listExtremes.get(listExtremes.size() - 1).getMaxPrice())
                        .subtract(new BigDecimal(listExtremes.get(listExtremes.size() - 2).getMinPrice()))
                        .divide(new BigDecimal("5"), 2, RoundingMode.DOWN)
                        .divide(new BigDecimal("2"), 2, RoundingMode.DOWN);

        String lastHighBorder =
                new BigDecimal(trendDetails.getMiddles().get("secondMiddle"))
                        .add(radiusSecondRange)
                        .toString();
        String lastLowBorder =
                new BigDecimal(trendDetails.getMiddles().get("secondMiddle"))
                        .subtract(radiusSecondRange)
                        .toString();

        return new RangeDetails(
                trendDetails,
                trendDetails.getExtremes().get(listExtremes.size() - 2),
                trendDetails.getExtremes().get(listExtremes.size() - 1),
                lastHighBorder,
                lastLowBorder
        );
    }

    private RangeDetails computeRangeDetailsFromGeneralRange(TrendDetails trendDetails){
        List<? extends Candle> listExtremes = trendDetails.getExtremes();
        String middleGeneralRange = computeMiddleGeneralRange(trendDetails);

        BigDecimal radiusGeneralRange =
                new BigDecimal(listExtremes.get(listExtremes.size() - 1).getMaxPrice())
                        .subtract(new BigDecimal(listExtremes.get(listExtremes.size() - 4).getMinPrice()))
                        .divide(new BigDecimal("5"), 2, RoundingMode.DOWN)
                        .divide(new BigDecimal("2"), 2, RoundingMode.DOWN);

        String generalHighBorder =
                new BigDecimal(middleGeneralRange)
                        .add(radiusGeneralRange)
                        .toString();
        String generalLowBorder =
                new BigDecimal(middleGeneralRange)
                        .subtract(radiusGeneralRange)
                        .toString();

        return new RangeDetails(
                trendDetails,
                trendDetails.getExtremes().get(listExtremes.size() - 4),
                trendDetails.getExtremes().get(listExtremes.size() - 1),
                generalHighBorder,
                generalLowBorder
        );
    }

    private String computeMiddleGeneralRange(TrendDetails trendDetails){
        return new BigDecimal(trendDetails.getExtremes().get(trendDetails.getExtremes().size() - 1).getMaxPrice())
                .subtract(new BigDecimal(trendDetails.getExtremes().get(trendDetails.getExtremes().size() - 4).getMinPrice()))
                .divide(new BigDecimal("2"), 2, RoundingMode.DOWN)
                .add(new BigDecimal(trendDetails.getExtremes().get(trendDetails.getExtremes().size() - 4).getMinPrice()))
                .toString();
    }

}
