package com.eduard.diploma.trader.Services.SpeculatorService.Details;

import com.eduard.diploma.trader.Models.Candles.Candle;
import com.eduard.diploma.trader.Models.Candles.Enums.KindsCandles;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Data
public class RangeDetails extends TrendDetails {
    private Candle firstCandle;
    private Candle lastCandle;

    private String topTradingRange;
    private String bottomTradingRange;

    public RangeDetails(KindsCandles typeRange,
                        List<? extends Candle> extremes,
                        Map<String, String> middles,
                        Candle firstCandle,
                        Candle lastCandle,
                        String topTradingRange,
                        String bottomTradingRange) {
        super(typeRange, extremes, middles);
        this.firstCandle = firstCandle;
        this.lastCandle = lastCandle;
        this.topTradingRange = topTradingRange;
        this.bottomTradingRange = bottomTradingRange;
    }

    public RangeDetails(TrendDetails trendDetails,
                        Candle firstCandle,
                        Candle lastCandle,
                        String topTradingRange,
                        String bottomTradingRange) {
        super(trendDetails.getKindCandle(), trendDetails.getExtremes(), trendDetails.getMiddles());
        this.firstCandle = firstCandle;
        this.lastCandle = lastCandle;
        this.topTradingRange = topTradingRange;
        this.bottomTradingRange = bottomTradingRange;
    }

}
