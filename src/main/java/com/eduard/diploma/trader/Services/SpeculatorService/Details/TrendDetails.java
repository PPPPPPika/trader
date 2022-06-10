package com.eduard.diploma.trader.Services.SpeculatorService.Details;

import com.eduard.diploma.trader.Models.Candles.Candle;
import com.eduard.diploma.trader.Models.Candles.Enums.KindsCandles;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class TrendDetails {
    private KindsCandles kindCandle;
    private List<? extends Candle> extremes;
    private Map<String, String> middles;

    public TrendDetails(KindsCandles kindCandle, List<? extends Candle> extremes, Map<String, String> middles) {
        this.kindCandle = kindCandle;
        this.extremes = extremes;
        this.middles = middles;
    }
}
