package com.eduard.diploma.trader.Test5;

import com.eduard.diploma.trader.Test2.BinanceData;
import com.eduard.diploma.trader.Test4.ThreeBody;

import java.util.List;

public class Main5 {
    public static void main(String[] args) {
        List<BinanceData> list =
                BinanceData.getBinanceData_5(1648771200000L).stream()
                        .map(candlestick -> new BinanceData(candlestick.getOpenTime(), candlestick.getOpen(),
                                candlestick.getCloseTime(), candlestick.getClose(),
                                candlestick.getHigh(), candlestick.getLow()))
                        .toList();

        RailsPattern railsPattern = new RailsPattern();
        System.out.println(railsPattern.applyPattern(list));
    }
}
