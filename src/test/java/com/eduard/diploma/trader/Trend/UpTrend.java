package com.eduard.diploma.trader.Trend;

import com.eduard.diploma.trader.Test2.BinanceData;

import java.util.ArrayList;
import java.util.List;

public class UpTrend {
    public List<BinanceData> getLineSupport(List<BinanceData> list){

        List<BinanceData> minimumList = new ArrayList<>();
        BinanceData currentMinimumCandle = list.get(list.size() - 1);
        int indexMinimumCandle = 0;

        for (int i = list.size() - 2; i > 0; i--){
            if (Double.parseDouble(list.get(i).getMinPrice()) < Double.parseDouble(currentMinimumCandle.getMinPrice())){
                currentMinimumCandle = list.get(i);
                indexMinimumCandle = i;
                minimumList.add(currentMinimumCandle);
            }
        }

        List<BinanceData> maximumList = new ArrayList<>();
        BinanceData currentMaximum = list.get(indexMinimumCandle);
        //int indexCurrentMaximum = 0;

        for (int i = indexMinimumCandle; i < list.size() - 1; i++){
            if (Double.parseDouble(list.get(i).getMaxPrice()) > Double.parseDouble(currentMaximum.getMaxPrice())){
                currentMaximum = list.get(i);
                maximumList.add(currentMaximum);
            }
        }
        return minimumList;
    }

}
