package com.eduard.diploma.trader.Test2;

import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        List<BinanceData> list =
                BinanceData.getBinanceData_1(1647388800000L)
                //BinanceData.getBinanceData_1440(1642982400000L)
                //BinanceData.getBinanceData_5(1646784000000L)
                        .stream()
                        .map(candlestick -> new BinanceData(candlestick.getOpenTime(), candlestick.getOpen(),
                                                              candlestick.getCloseTime(), candlestick.getClose(),
                                                              candlestick.getHigh(), candlestick.getLow()))
                        .toList();

        //System.out.println(list);

        ProcessorBinanceData processorBinanceData = new ProcessorBinanceData();
        //System.out.println(processorBinanceData.minPoint(list));
        //System.out.println(processorBinanceData.starPattern(list));
        System.out.println(processorBinanceData.hammerPattern(list));

    }


}
