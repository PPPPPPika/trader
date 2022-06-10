package com.eduard.diploma.trader.Test2;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

//@AllArgsConstructor
@NoArgsConstructor
public class ProcessorBinanceData {
    public BinanceData minPoint(List<BinanceData> list){
        int index = 0;
        double value = Double.parseDouble(list.get(0).getMinPrice());
        for (int i = 1; i < list.size(); i++){
            if (Double.parseDouble(list.get(i).getMinPrice()) < value){
                index = i;
                value = Double.parseDouble(list.get(i).getMinPrice());
            }
        }
        return list.get(index);
    }

    public BigDecimal supportPercent(List<BinanceData> list){ //support line
        return list.stream()
                .parallel()
                .sorted(Comparator.comparingDouble(value -> Double.parseDouble(value.getMinPrice())))
                .limit(2)
                .map(binanceData -> new BigDecimal(binanceData.getMinPrice()))
                .reduce((value1, value2) ->
                        value2.multiply(new BigDecimal("100"))
                                .divide(value1, 2, RoundingMode.DOWN)
                                .subtract(new BigDecimal("100"))
                )
                .get();
    }

    public List<BinanceData> starPattern(List<BinanceData> binanceDataList){ // патерн звезды с нисходящего тренда на восходящий //таймфрейм 5 минут
        List<BinanceData> newBinanceDataList = new ArrayList<>();

        for (int i = 3; i < binanceDataList.size(); i++){
            System.out.println(binanceDataList.get(i));
            if (Double.parseDouble(binanceDataList.get(i).getClosePrice()) < Double.parseDouble(binanceDataList.get(i).getOpenPrice())){
                System.out.println("Double.parseDouble(binanceDataList.get(i).getClosePrice()): " + Double.parseDouble(binanceDataList.get(i).getClosePrice()));
                System.out.println("Double.parseDouble(binanceDataList.get(i).getOpenPrice()): " + Double.parseDouble(binanceDataList.get(i).getOpenPrice()));

                if (checkNegativeNumber(new ArrayList<>(List.of(binanceDataList.get(i - 1), binanceDataList.get(i - 2), binanceDataList.get(i - 3))))){
                    /*BigDecimal value = new BigDecimal(binanceDataList.get(i - 1).getClosePrice()).subtract(new BigDecimal(binanceDataList.get(i - 3).getOpenPrice()));
                    System.out.println("value: " + value);*/

                    BigDecimal number = new BigDecimal(binanceDataList.get(i).getClosePrice())
                            .multiply(new BigDecimal("100"))
                            .divide(new BigDecimal(binanceDataList.get(i).getOpenPrice()), 2, RoundingMode.DOWN)
                            .subtract(new BigDecimal("100"));
                    System.out.println("number: " + number);
                    System.out.println("number.doubleValue(): " + number.doubleValue());

                    BigDecimal numberMin = new BigDecimal(binanceDataList.get(i).getMinPrice())
                            .multiply(new BigDecimal("100"))
                            .divide(new BigDecimal(binanceDataList.get(i).getOpenPrice()), 2, RoundingMode.DOWN)
                            .subtract(new BigDecimal("100"));
                    System.out.println("numberMin" + numberMin);
                    System.out.println("numberMin.doubleValue(): " + numberMin.doubleValue());

                    BigDecimal numberMax = new BigDecimal(binanceDataList.get(i).getMaxPrice())
                            .multiply(new BigDecimal("100"))
                            .divide(new BigDecimal(binanceDataList.get(i).getOpenPrice()), 2, RoundingMode.DOWN) // ошибка
                            .subtract(new BigDecimal("100"));
                    System.out.println("numberMax: " + numberMax);
                    System.out.println("numberMax.doubleValue(): " + numberMax.doubleValue());

                    if (number.multiply(new BigDecimal("4")).doubleValue() > numberMin.doubleValue() &&
                            number.negate().multiply(new BigDecimal("4")).doubleValue() < numberMax.doubleValue()){
                        newBinanceDataList.add(binanceDataList.get(i));
                    }
                }
            }
        }
        return newBinanceDataList;
    }

    private boolean checkNegativeNumber(List<BinanceData> binanceDataList){
        for (BinanceData binanceData : binanceDataList) {
            double value = Double.parseDouble(binanceData.getClosePrice()) - Double.parseDouble(binanceData.getOpenPrice());
            if (value > 0)
                return false;
        }
        return true;
    }





    public List<BinanceData> hammerPattern(List<BinanceData> binanceDataList) {
        List<BinanceData> newBinanceDataList = new ArrayList<>();

        for (int i = 3; i < binanceDataList.size(); i++) {
            System.out.println(binanceDataList.get(i));

            if (Double.parseDouble(binanceDataList.get(i).getClosePrice()) > Double.parseDouble(binanceDataList.get(i).getOpenPrice())) {
                System.out.println("Double.parseDouble(binanceDataList.get(i).getClosePrice()): " + Double.parseDouble(binanceDataList.get(i).getClosePrice()));
                System.out.println("Double.parseDouble(binanceDataList.get(i).getOpenPrice()): " + Double.parseDouble(binanceDataList.get(i).getOpenPrice()));

                BigDecimal number = new BigDecimal(binanceDataList.get(i).getClosePrice())
                        .multiply(new BigDecimal("100"))
                        .divide(new BigDecimal(binanceDataList.get(i).getOpenPrice()), 2, RoundingMode.DOWN)
                        .subtract(new BigDecimal("100"));
                System.out.println("number: " + number);
                System.out.println("number.doubleValue(): " + number.doubleValue());

                if (number.doubleValue() < 0.03){ //
                    if (checkNegativeNumber(new ArrayList<>(List.of(binanceDataList.get(i - 1), binanceDataList.get(i - 2), binanceDataList.get(i - 3))))) {

                        BigDecimal numberMin = new BigDecimal(binanceDataList.get(i).getMinPrice())
                                .multiply(new BigDecimal("100"))
                                .divide(new BigDecimal(binanceDataList.get(i).getOpenPrice()), 3, RoundingMode.DOWN)
                                .subtract(new BigDecimal("100"));
                        System.out.println("numberMin: " + numberMin);
                        System.out.println("numberMin.doubleValue(): " + numberMin.doubleValue());

                        BigDecimal numberMax = new BigDecimal(binanceDataList.get(i).getMaxPrice())
                                .multiply(new BigDecimal("100"))
                                .divide(new BigDecimal(binanceDataList.get(i).getOpenPrice()), 3, RoundingMode.DOWN)
                                .subtract(new BigDecimal("100"));
                        System.out.println("numberMax: " + numberMax);
                        System.out.println("numberMax.doubleValue(): " + numberMax.doubleValue());


                        System.out.println("number.negate().multiply(new BigDecimal(4)).doubleValue()" + number.negate().multiply(new BigDecimal("4")).doubleValue());
                        System.out.println("number.divide(new BigDecimal(\"3\"), 3, RoundingMode.DOWN).doubleValue()" + number.divide(new BigDecimal("3"), 3, RoundingMode.DOWN).doubleValue());

                        if (numberMin.doubleValue() > number.negate().divide(new BigDecimal("3"), 3, RoundingMode.DOWN).doubleValue() &&
                            number.multiply(new BigDecimal("5"))/*.subtract(number)*/.doubleValue() < numberMax.doubleValue()){
                            newBinanceDataList.add(binanceDataList.get(i));
                        }
                        else if (numberMin.doubleValue() < number.negate().multiply(new BigDecimal("4")).doubleValue() &&
                                 numberMax.doubleValue() < number.divide(new BigDecimal("3"), 3, RoundingMode.DOWN).doubleValue()){
                            newBinanceDataList.add(binanceDataList.get(i));
                        }
                    }
                }
            }
        }
        return newBinanceDataList;
    }












}
