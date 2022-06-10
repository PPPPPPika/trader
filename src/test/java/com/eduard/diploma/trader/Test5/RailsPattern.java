package com.eduard.diploma.trader.Test5;

import com.eduard.diploma.trader.Test2.BinanceData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class RailsPattern {
    public List<BinanceData> applyPattern(List<BinanceData> list){
        List<BinanceData> newList = new ArrayList<>();

        for (int i = 5; i < list.size(); i++){
            System.out.println(list.get(i));

            BigDecimal currentCandleSing = new BigDecimal(list.get(i).getClosePrice()).subtract(new BigDecimal(list.get(i).getOpenPrice()));
            BigDecimal penultimateCandleSing = new BigDecimal(list.get(i - 1).getClosePrice()).subtract(new BigDecimal(list.get(i - 1).getOpenPrice()));
            System.out.println("currentCandleSing" + " | " + currentCandleSing);
            System.out.println("penultimateCandleSing" + " | " + penultimateCandleSing);
            if (currentCandleSing.doubleValue() > 0 && penultimateCandleSing.doubleValue() < 0){
                BigDecimal trend = new BigDecimal(list.get(i - 1).getClosePrice()).subtract(new BigDecimal(list.get(i - 5).getOpenPrice()));
                System.out.println("trend" + " | " + trend);
                if (trend.doubleValue() < 0){
                    BigDecimal difference;
                    BigDecimal firstCandleSing = new BigDecimal(list.get(i - 5).getClosePrice()).subtract(new BigDecimal(list.get(i - 5).getOpenPrice()));
                    System.out.println("firstCandleSing" + " | " + firstCandleSing);
                    if (firstCandleSing.doubleValue() > 0)
                        difference =
                                new BigDecimal(list.get(i - 1).getClosePrice())
                                        .multiply(new BigDecimal("100"))
                                        .divide(new BigDecimal(list.get(i - 5).getOpenPrice()), 2, RoundingMode.DOWN)
                                        .subtract(new BigDecimal("100"))
                                        .negate();
                    else
                        difference =
                                new BigDecimal(list.get(i - 1).getClosePrice())
                                        .multiply(new BigDecimal("100"))
                                        .divide(new BigDecimal(list.get(i - 5).getClosePrice()), 2, RoundingMode.DOWN)
                                        .subtract(new BigDecimal("100"))
                                        .negate();
                    System.out.println("difference" + " | " + difference);
                    if (difference.doubleValue() >= 0.3 ||
                            checkTrend(new ArrayList<>(List.of(list.get(i - 1), list.get(i - 2), list.get(i - 3), list.get(i - 4), list.get(i - 5))))){
                        BigDecimal currentBodyCandle = new BigDecimal(list.get(i).getMaxPrice()).subtract(new BigDecimal(list.get(i).getMinPrice()));
                        BigDecimal allCurrentCandle = new BigDecimal(list.get(i).getClosePrice()).subtract(new BigDecimal(list.get(i).getOpenPrice()));
                        System.out.println("currentBodyCandle" + " | " + currentBodyCandle);
                        System.out.println("allCurrentCandle" + " | " + allCurrentCandle);
                        BigDecimal proportionCurrentCandle =
                                allCurrentCandle
                                        .multiply(new BigDecimal("100"))
                                        .divide(currentBodyCandle, 2, RoundingMode.DOWN);
                        BigDecimal penultimateBodyCandle = new BigDecimal(list.get(i - 1).getMaxPrice()).subtract(new BigDecimal(list.get(i - 1).getMinPrice()));
                        BigDecimal allPenultimateCandle = new BigDecimal(list.get(i - 1).getOpenPrice()).subtract(new BigDecimal(list.get(i - 1).getClosePrice()));
                        BigDecimal proportionPenultimateCandle =
                                allPenultimateCandle
                                        .multiply(new BigDecimal("100"))
                                        .divide(penultimateBodyCandle, 2, RoundingMode.DOWN);
                        System.out.println("proportionCurrentCandle" + " | " + proportionCurrentCandle);
                        System.out.println("penultimateBodyCandle" + " | " + penultimateBodyCandle);
                        System.out.println("allPenultimateCandle" + " | " + allPenultimateCandle);
                        System.out.println("proportionPenultimateCandle" + " | " + proportionPenultimateCandle);
                        if (proportionCurrentCandle.doubleValue() >= 70 && proportionPenultimateCandle.doubleValue() >= 70){
                            //сравнить размеры 1 и 2 свечей
                            BigDecimal currentCandleChange = new BigDecimal(list.get(i).getClosePrice()).subtract(new BigDecimal(list.get(i).getOpenPrice()));
                            BigDecimal penultimateCandleChange = new BigDecimal(list.get(i - 1).getOpenPrice()).subtract(new BigDecimal(list.get(i - 1).getClosePrice()));
                            System.out.println("currentCandleChange" + " | " + currentCandleChange);
                            System.out.println("penultimateCandleChange" + " | " + penultimateCandleChange);
                            BigDecimal compareProportion1 =
                                    currentCandleChange
                                            .multiply(new BigDecimal("100"))
                                            .divide(penultimateCandleChange, 2, RoundingMode.DOWN);
                            BigDecimal compareProportion2 =
                                    penultimateCandleChange
                                            .multiply(new BigDecimal("100"))
                                            .divide(currentCandleChange, 2, RoundingMode.DOWN);
                            System.out.println("compareProportion1" + " | " + compareProportion1);
                            System.out.println("compareProportion2" + " | " + compareProportion2);
                            if ((compareProportion1.doubleValue() > 85 && compareProportion1.doubleValue() < 125) ||
                                    (compareProportion2.doubleValue() > 85 && compareProportion2.doubleValue() < 125)){
                                newList.add(list.get(i));
                            }
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
