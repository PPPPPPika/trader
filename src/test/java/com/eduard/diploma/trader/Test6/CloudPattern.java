package com.eduard.diploma.trader.Test6;

import com.eduard.diploma.trader.Test2.BinanceData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CloudPattern {
    public List<BinanceData> applyPattern(List<BinanceData> list){
        List<BinanceData> newList = new ArrayList<>();

        int sizeList = list.size();
        for (int i = 5; i < sizeList; i++){
            System.out.println(list.get(i));

            BigDecimal currentCandleSing = new BigDecimal(list.get(i).getClosePrice()).subtract(new BigDecimal(list.get(i).getOpenPrice()));
            BigDecimal penultimateCandleSing = new BigDecimal(list.get(i - 1).getClosePrice()).subtract(new BigDecimal(list.get(i - 1).getOpenPrice()));
            System.out.println("currentCandleSing" + " | " + currentCandleSing);
            System.out.println("penultimateCandleSing" + " | " + penultimateCandleSing);
            if (currentCandleSing.doubleValue() > 0 && penultimateCandleSing.doubleValue() < 0){
                BigDecimal trend = new BigDecimal(list.get(i - 1).getClosePrice()).subtract(new BigDecimal(list.get(i - 5).getOpenPrice()));
                System.out.println("trend" + " | " + trend);
                if (trend.doubleValue() < 0){
                    BigDecimal differenceTrend;
                    BigDecimal firstCandleSing = new BigDecimal(list.get(i - 5).getClosePrice()).subtract(new BigDecimal(list.get(i - 5).getOpenPrice()));
                    System.out.println("firstCandleSing" + " | " + firstCandleSing);
                    if (firstCandleSing.doubleValue() > 0)
                        differenceTrend =
                                new BigDecimal(list.get(i - 1).getClosePrice())
                                        .multiply(new BigDecimal("100"))
                                        .divide(new BigDecimal(list.get(i - 5).getOpenPrice()), 2, RoundingMode.DOWN)
                                        .subtract(new BigDecimal("100"))
                                        .negate();
                    else
                        differenceTrend =
                                new BigDecimal(list.get(i - 1).getClosePrice())
                                        .multiply(new BigDecimal("100"))
                                        .divide(new BigDecimal(list.get(i - 5).getClosePrice()), 2, RoundingMode.DOWN)
                                        .subtract(new BigDecimal("100"))
                                        .negate();
                    System.out.println("differenceTrend" + " | " + differenceTrend);
                    if (differenceTrend.doubleValue() >= 0.3 ||
                            checkTrend(new ArrayList<>(List.of(list.get(i - 1), list.get(i - 2), list.get(i - 3), list.get(i - 4), list.get(i - 5))))){
                        BigDecimal difference =
                                new BigDecimal(list.get(i - 1).getClosePrice())
                                        .subtract(new BigDecimal(list.get(i).getOpenPrice()));
                        System.out.println("difference" + " | " + difference);
                        if (difference.doubleValue() < 2){
                            newList.add(list.get(i));
                        }
                    }
                }
            }
        }
        return newList;
    }

    private boolean checkTrend(List<BinanceData> list){
        boolean result = true;
        int listSize = list.size();
        for (int i = 0; i < listSize; i++){
            if (Double.parseDouble(list.get(i).getClosePrice()) > Double.parseDouble(list.get(i).getOpenPrice())){
                result = false;
                break;
            }
        }
        return result;
    }
}
