package com.eduard.diploma.trader.Test6;

import com.eduard.diploma.trader.Test2.BinanceData;
import com.eduard.diploma.trader.Test5.RailsPattern;

import java.util.List;

public class Main6 {
    public static void main(String[] args) {
        List<BinanceData> list =
                BinanceData.getBinanceData_5(1648771200000L).stream()
                        .map(candlestick -> new BinanceData(candlestick.getOpenTime(), candlestick.getOpen(),
                                candlestick.getCloseTime(), candlestick.getClose(),
                                candlestick.getHigh(), candlestick.getLow()))
                        .toList();

        CloudPattern cloudPattern = new CloudPattern();
        System.out.println(cloudPattern.applyPattern(list));
    }
}
