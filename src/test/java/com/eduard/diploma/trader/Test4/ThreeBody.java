package com.eduard.diploma.trader.Test4;

import com.eduard.diploma.trader.Test2.BinanceData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ThreeBody {
    public List<BinanceData> applyPattern(List<BinanceData> list){ // последняя свеча не должна быть намного больше
        List<BinanceData> newList = new ArrayList<>();

        for (int i = 2; i < list.size(); i++){
            System.out.println(list.get(i));

            BigDecimal currentCandleSing = new BigDecimal(list.get(i).getClosePrice()).subtract(new BigDecimal(list.get(i).getOpenPrice()));
            BigDecimal penultimateCandleSing = new BigDecimal(list.get(i - 1).getClosePrice()).subtract(new BigDecimal(list.get(i - 1).getOpenPrice()));
            BigDecimal firstCandleSing = new BigDecimal(list.get(i - 2).getClosePrice()).subtract(new BigDecimal(list.get(i - 2).getOpenPrice()));
            System.out.println("currentCandle.doubleValue()" + " : " + currentCandleSing.doubleValue());
            System.out.println("penultimateCandle.doubleValue()" + " : " + penultimateCandleSing.doubleValue());
            System.out.println("firstCandle.doubleValue()" + " : " + firstCandleSing.doubleValue());
            if (currentCandleSing.doubleValue() > 0 && penultimateCandleSing.doubleValue() > 0 && firstCandleSing.doubleValue() > 0){
                BigDecimal currentCandleChange =
                        new BigDecimal(list.get(i).getClosePrice())
                                .multiply(new BigDecimal("100"))
                                .divide(new BigDecimal(list.get(i).getOpenPrice()), 2, RoundingMode.HALF_UP)
                                .subtract(new BigDecimal("100"));
                BigDecimal penultimateCandleChange =
                        new BigDecimal(list.get(i - 1).getClosePrice())
                                .multiply(new BigDecimal("100"))
                                .divide(new BigDecimal(list.get(i - 1).getOpenPrice()), 2, RoundingMode.HALF_UP)
                                .subtract(new BigDecimal("100"));
                System.out.println("currentCandleChange" + " : " + currentCandleChange.doubleValue());
                System.out.println("penultimateCandleChange" + " : " + penultimateCandleChange.doubleValue());

                if (currentCandleChange.doubleValue() >= penultimateCandleChange.doubleValue()){
                    if (Double.parseDouble(list.get(i - 1).getClosePrice()) > Double.parseDouble(list.get(i - 2).getMaxPrice()) &&
                            Double.parseDouble(list.get(i - 1).getMinPrice()) > Double.parseDouble(list.get(i - 2).getOpenPrice()) &&
                            Double.parseDouble(list.get(i - 1).getMaxPrice()) < Double.parseDouble(list.get(i).getClosePrice()) &&
                            Double.parseDouble(list.get(i).getMinPrice()) > Double.parseDouble(list.get(i - 1).getOpenPrice())){
                        BigDecimal firstCandleChange =
                                new BigDecimal(list.get(i - 2).getClosePrice())
                                        .multiply(new BigDecimal("100"))
                                        .divide(new BigDecimal(list.get(i - 2).getOpenPrice()), 2, RoundingMode.HALF_UP)
                                        .subtract(new BigDecimal("100"));
                        System.out.println("firstCandleChange" + " : " + firstCandleChange);
                        BigDecimal difference = penultimateCandleChange.subtract(firstCandleChange);
                        System.out.println("difference" + " : " + difference);
                        if (difference.doubleValue() >= 0){
                            newList.add(list.get(i));
                            for (int j = i + 1; j < list.size(); j++){
                                BigDecimal candleSing = new BigDecimal(list.get(j).getClosePrice()).subtract(new BigDecimal(list.get(j).getOpenPrice()));
                                System.out.println("candleSingAfterResult" + " : " + candleSing);
                                if (candleSing.doubleValue() < 0){
                                    i = j;
                                    break;
                                }
                            }

                        }
                    }

                }
            }
        }
        return newList;
    }
}

/**/
