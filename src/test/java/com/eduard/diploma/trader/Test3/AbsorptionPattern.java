package com.eduard.diploma.trader.Test3;

import com.eduard.diploma.trader.Test2.BinanceData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class AbsorptionPattern {
    public List<BinanceData> applyPattern(List<BinanceData> list){
        List<BinanceData> resultList = new ArrayList<>();

        for (int i = 5; i < list.size(); i++){
            System.out.println(list.get(i));
            BigDecimal singCandle = new BigDecimal(list.get(i).getClosePrice()).subtract(new BigDecimal(list.get(i).getOpenPrice()));
            System.out.println("singCandle" + " : " + singCandle.doubleValue());
            if (singCandle.doubleValue() > 0){
                BigDecimal penultimateCandle = new BigDecimal(list.get(i - 1).getClosePrice()).subtract(new BigDecimal(list.get(i - 1).getOpenPrice()));
                System.out.println("penultimateCandle" + " : " + penultimateCandle.doubleValue());
                BigDecimal penultimateCandleChange =
                        new BigDecimal(list.get(i - 1).getClosePrice())
                                .multiply(new BigDecimal("100"))
                                .divide(new BigDecimal(list.get(i - 1).getOpenPrice()), 2, RoundingMode.HALF_UP)
                                .subtract(new BigDecimal("100"));
                if (penultimateCandle.doubleValue() < 0 && penultimateCandleChange.doubleValue() <= -0.03){
                    BigDecimal trend = new BigDecimal(list.get(i - 1).getClosePrice()).subtract(new BigDecimal(list.get(i - 5).getOpenPrice()));
                    System.out.println("trend" + " : " + trend.doubleValue());

                    BigDecimal firstCandleSing = new BigDecimal(list.get(i - 5).getClosePrice()).subtract(new BigDecimal(list.get(i - 5).getOpenPrice()));
                    BigDecimal difference;
                    if (firstCandleSing.doubleValue() > 0)
                        difference = new BigDecimal(list.get(i - 1).getClosePrice())
                                .multiply(new BigDecimal("100"))
                                .divide(new BigDecimal(list.get(i - 5).getOpenPrice()), 2, RoundingMode.DOWN)
                                .subtract(new BigDecimal("100"));
                    else
                        difference = new BigDecimal(list.get(i - 1).getClosePrice())
                                .multiply(new BigDecimal("100"))
                                .divide(new BigDecimal(list.get(i - 5).getClosePrice()), 2, RoundingMode.DOWN)
                                .subtract(new BigDecimal("100"));
                    System.out.println("difference" + " : " + difference.doubleValue());

                    if (trend.doubleValue() < 0 && difference.doubleValue() < -0.1){
                        BigDecimal penultimateCandleX2 =
                                new BigDecimal(list.get(i - 1).getClosePrice())
                                        .multiply(new BigDecimal("100"))
                                        .divide(new BigDecimal(list.get(i - 1).getOpenPrice()), 2, RoundingMode.HALF_UP)
                                        .subtract(new BigDecimal("100"))
                                        .negate()
                                        .multiply(new BigDecimal("2"));
                        System.out.println("penultimateCandleX2" + " : " + penultimateCandleX2.doubleValue());

                        BigDecimal currentChangeCandle =
                                new BigDecimal(list.get(i).getClosePrice())
                                        .multiply(new BigDecimal("100"))
                                        .divide(new BigDecimal(list.get(i).getOpenPrice()), 2, RoundingMode.HALF_UP)
                                        .subtract(new BigDecimal("100"));
                        System.out.println("currentChangeCandle" + " : " + currentChangeCandle.doubleValue());
                        if (currentChangeCandle.doubleValue() > penultimateCandleX2.doubleValue()){
                            resultList.add(list.get(i));
                        }
                    }
                }
            }
        }
        return resultList;
    }
}
