package com.eduard.diploma.trader.Services.Receiver;

import com.eduard.diploma.trader.Models.Candles.Enums.KindsCandles;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TimeManager {
    private final static Map<KindsCandles, int[]> mapWorkTime =
            new HashMap<>(
                    Map.of(KindsCandles.THREE, new int[]{3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36, 39, 42, 45, 48, 51, 54, 57, 0},
                            KindsCandles.FIVE, new int[]{5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 0},
                            KindsCandles.FIFTEEN, new int[]{15, 30, 45, 0},
                            KindsCandles.THIRTY, new int[]{30, 0},
                            KindsCandles.SIXTY, new int[]{0})
            );

    public KindsCandles[] identifyCurrentCandles(int currentMinute){
        KindsCandles[] currentCandles = new KindsCandles[5];
        int index = 0;
        for (Map.Entry<KindsCandles, int[]> workTime : mapWorkTime.entrySet()){
            if (checkTimeInterval(workTime.getValue(), currentMinute)){
                currentCandles[index] = workTime.getKey();
            }
            index++;
        }
        return currentCandles;
    }

    private boolean checkTimeInterval(int[] workTime, int currentMinute){
        for (int i = 0; i < workTime.length; i++){
            if (workTime[i] == currentMinute)
                return true;
        }
        return false;
    }
}
