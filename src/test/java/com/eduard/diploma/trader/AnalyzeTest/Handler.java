package com.eduard.diploma.trader.AnalyzeTest;

import com.eduard.diploma.trader.Models.Candles.Candle;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Handler {
    public <CandleType extends Candle> List<? extends Candle> processingPoints(List<CandleType> listPoints, String key){
        List<CandleType> updatedPointsList = new LinkedList<>(List.of(listPoints.get(0)));
        long interval = 60000;

        List<CandleType> bufferList = new LinkedList<>();
        int listSize = listPoints.size();
        for (int i = 1; i < listSize; i++){
            if (listPoints.get(i - 1).getCloseTime() + interval != listPoints.get(i).getCloseTime())
                updatedPointsList.add(listPoints.get(i));
            else {
                bufferList.add(listPoints.get(i - 1));
                bufferList.add(listPoints.get(i));

                if (i + 1 != listSize){
                    for (int j = i; j < listSize - 1; j++){
                        if (listPoints.get(j).getCloseTime() + interval == listPoints.get(j + 1).getCloseTime()){
                            bufferList.add(listPoints.get(j + 1));
                            if (j + 1 != listSize)
                                i = j + 1;
                            else
                                i = j;
                        }
                        else
                            break;
                    }
                }

                if (key.equals("Maximums"))
                    updatedPointsList.add(bufferList.stream().max(Comparator.comparing(value -> Double.parseDouble(value.getMaxPrice()))).get());
                else if (key.equals("Minimums"))
                    updatedPointsList.add(bufferList.stream().min(Comparator.comparing(value -> Double.parseDouble(value.getMinPrice()))).get());

                bufferList.clear();
            }
        }

        /*if (key.equals("Maximums"))
            System.out.println("Maximums processed: " + "\n" + updatedPointsList);
        else
            System.out.println("Minimums processed: " + "\n" + updatedPointsList);*/

        return updatedPointsList;
    }



}
