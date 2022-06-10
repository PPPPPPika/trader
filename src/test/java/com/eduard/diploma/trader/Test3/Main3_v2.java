package com.eduard.diploma.trader.Test3;

import com.eduard.diploma.trader.Test2.BinanceData;

import java.util.List;

public class Main3_v2 {
    public static void main(String[] args) {
        List<BinanceData> list =
                BinanceData.getBinanceData_5(1648771200000L).stream()
                        .map(candlestick -> new BinanceData(candlestick.getOpenTime(), candlestick.getOpen(),
                                candlestick.getCloseTime(), candlestick.getClose(),
                                candlestick.getHigh(), candlestick.getLow()))
                        .toList();

        AbsorptionPattern absorptionPattern = new AbsorptionPattern();

        System.out.println(absorptionPattern.applyPattern(list));
    }
}
