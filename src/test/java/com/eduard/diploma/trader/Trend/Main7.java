package com.eduard.diploma.trader.Trend;

import com.eduard.diploma.trader.Test2.BinanceData;

import java.util.List;

public class Main7 {
    public static void main(String[] args) {
        List<BinanceData> list =
                BinanceData.getBinanceData_5_36(1649635200000L).stream()
                        .map(candlestick ->
                                new BinanceData(candlestick.getOpenTime(), candlestick.getOpen(),
                                        candlestick.getCloseTime(), candlestick.getClose(),
                                        candlestick.getHigh(), candlestick.getLow())
                        )
                        .toList();

        DownTrend downTrend = new DownTrend();
        //System.out.println(new Date(1649635200000L));
        System.out.println(downTrend.getLineResistance(list));

    }
}

