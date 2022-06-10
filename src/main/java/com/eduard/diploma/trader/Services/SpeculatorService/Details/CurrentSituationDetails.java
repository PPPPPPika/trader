package com.eduard.diploma.trader.Services.SpeculatorService.Details;

import com.eduard.diploma.trader.Models.Candles.Candle;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurrentSituationDetails extends RangeDetails{
    private Candle currentCandle;

    public CurrentSituationDetails(RangeDetails rangeDetails, Candle currentCandle) {
        super(rangeDetails.getKindCandle(),
                rangeDetails.getExtremes(),
                rangeDetails.getMiddles(),
                rangeDetails.getFirstCandle(),
                rangeDetails.getLastCandle(),
                rangeDetails.getTopTradingRange(),
                rangeDetails.getBottomTradingRange());
        this.currentCandle = currentCandle;
    }

    public boolean isEmpty(){
        return currentCandle == null;
    }
}
