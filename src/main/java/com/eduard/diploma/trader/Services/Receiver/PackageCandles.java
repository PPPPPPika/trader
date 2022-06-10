package com.eduard.diploma.trader.Services.Receiver;

import com.eduard.diploma.trader.Models.Candles.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PackageCandles {
    private OneMinuteCandle oneMinuteCandle;
    private ThreeMinuteCandle threeMinuteCandle;
    private FiveMinuteCandle fiveMinuteCandle;
    private FifteenMinuteCandle fifteenMinuteCandle;
    private ThirtyMinuteCandle thirtyMinuteCandle;
    private SixtyMinuteCandle sixtyMinuteCandle;

    public PackageCandles(Object candle) {
        this.oneMinuteCandle = (OneMinuteCandle) candle;
    }

    public PackageCandles(Object candle1, Object candle2) {
        if (candle1 instanceof OneMinuteCandle && candle2 instanceof ThreeMinuteCandle){
            this.oneMinuteCandle = (OneMinuteCandle) candle1;
            this.threeMinuteCandle = (ThreeMinuteCandle) candle2;
        }
        else if (candle1 instanceof OneMinuteCandle && candle2 instanceof FiveMinuteCandle){
            this.oneMinuteCandle = (OneMinuteCandle) candle1;
            this.fiveMinuteCandle = (FiveMinuteCandle) candle2;
        }
    }

    public PackageCandles(Object candle1, Object candle2, Object candle3, Object candle4) {
        if (candle1 instanceof OneMinuteCandle && candle2 instanceof ThreeMinuteCandle &&
                candle3 instanceof FiveMinuteCandle && candle4 instanceof FifteenMinuteCandle){
            this.oneMinuteCandle = (OneMinuteCandle) candle1;
            this.threeMinuteCandle = (ThreeMinuteCandle) candle2;
            this.fiveMinuteCandle = (FiveMinuteCandle) candle3;
            this.fifteenMinuteCandle = (FifteenMinuteCandle) candle4;
        }
    }

    public PackageCandles(Object candle1, Object candle2, Object candle3, Object candle4, Object candle5) {
        if (candle1 instanceof OneMinuteCandle && candle2 instanceof ThreeMinuteCandle && candle3 instanceof FiveMinuteCandle &&
                candle4 instanceof FifteenMinuteCandle && candle5 instanceof ThirtyMinuteCandle){
            this.oneMinuteCandle = (OneMinuteCandle) candle1;
            this.threeMinuteCandle = (ThreeMinuteCandle) candle2;
            this.fiveMinuteCandle = (FiveMinuteCandle) candle3;
            this.fifteenMinuteCandle = (FifteenMinuteCandle) candle4;
            this.thirtyMinuteCandle = (ThirtyMinuteCandle) candle5;
        }
    }

    public PackageCandles(Object candle1, Object candle2, Object candle3, Object candle4, Object candle5, Object candle6) {
        if (candle1 instanceof OneMinuteCandle && candle2 instanceof ThreeMinuteCandle &&
                candle3 instanceof FiveMinuteCandle && candle4 instanceof FifteenMinuteCandle &&
                candle5 instanceof ThirtyMinuteCandle && candle6 instanceof SixtyMinuteCandle){
            this.oneMinuteCandle = (OneMinuteCandle) candle1;
            this.threeMinuteCandle = (ThreeMinuteCandle) candle2;
            this.fiveMinuteCandle = (FiveMinuteCandle) candle3;
            this.fifteenMinuteCandle = (FifteenMinuteCandle) candle4;
            this.thirtyMinuteCandle = (ThirtyMinuteCandle) candle5;
            this.sixtyMinuteCandle = (SixtyMinuteCandle) candle6;
        }
    }

}
