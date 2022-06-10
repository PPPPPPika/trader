package com.eduard.diploma.trader.Test4;

import com.eduard.diploma.trader.Test2.BinanceData;
import com.eduard.diploma.trader.Test3.AbsorptionPattern;

import java.util.List;

public class Main4 {
    public static void main(String[] args) {
        List<BinanceData> list =
                BinanceData.getBinanceData_5(1648944000000L).stream()
                        .map(candlestick -> new BinanceData(candlestick.getOpenTime(), candlestick.getOpen(),
                                candlestick.getCloseTime(), candlestick.getClose(),
                                candlestick.getHigh(), candlestick.getLow()))
                        .toList();

        ThreeBody threeBody = new ThreeBody();
        System.out.println(threeBody.applyPattern(list));

    }
}

