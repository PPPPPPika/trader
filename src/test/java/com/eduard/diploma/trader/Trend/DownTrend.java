package com.eduard.diploma.trader.Trend;

import com.eduard.diploma.trader.Test2.BinanceData;

import java.util.ArrayList;
import java.util.List;

public class DownTrend {

    public List<BinanceData> getLineResistance(List<BinanceData> list){
        List<BinanceData> maximumList = new ArrayList<>();

        BinanceData currentMaximum = list.get(list.size() - 1);
        int indexCurrentMaximum = 0;
        for (int i = list.size() - 2; i > 0; i--){
            if (Double.parseDouble(list.get(i).getMaxPrice()) > Double.parseDouble(currentMaximum.getMaxPrice())){
                currentMaximum = list.get(i);
                indexCurrentMaximum = i;
                System.out.println("indexCurrentMaximum" + " | " + indexCurrentMaximum);
                System.out.println("currentMaximum" + " | " + currentMaximum);
                maximumList.add(currentMaximum);
            }
        }
        //System.out.println("maximumList" + " | " + maximumList);
        //System.out.println("currentMaximum" + " | " + currentMaximum);

        List<BinanceData> minimumList = new ArrayList<>();
        BinanceData currentMinimumCandle = list.get(indexCurrentMaximum);

        for (int i = indexCurrentMaximum + 1; i < list.size() - 1; i++){
            if (Double.parseDouble(list.get(i).getMinPrice()) < Double.parseDouble(currentMinimumCandle.getMinPrice())){
                currentMinimumCandle = list.get(i);
                minimumList.add(currentMinimumCandle);
            }
        }
        return minimumList;
    }

    /*public List<BinanceData> minimumPoints(List<BinanceData> list){
        List<BinanceData> trendPointsList = new ArrayList<>();
        BinanceData currentMinimumCandle = list.get(0);

        int listSize = list.size();
        for (int i = 1; i < listSize; i++){
            if (Double.parseDouble(list.get(i).getMinPrice()) < Double.parseDouble(currentMinimumCandle.getMinPrice())){
                currentMinimumCandle = list.get(i);
                trendPointsList.add(currentMinimumCandle);
            }
        }
        return trendPointsList;
    }

    public List<BinanceData> maximumPoints(List<BinanceData> list){
        List<BinanceData> maximumList = new ArrayList<>();

        BinanceData currentMaximum = list.get(list.size() - 1);
        for (int i = list.size() - 2; i > 0; i--){
            if (Double.parseDouble(list.get(i).getMaxPrice()) > Double.parseDouble(currentMaximum.getMaxPrice())){
                currentMaximum = list.get(i);
                maximumList.add(currentMaximum);
            }
        }
        return maximumList;
    }*/


}
