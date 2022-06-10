package com.eduard.diploma.trader.AnalyzeTest;

import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import com.eduard.diploma.trader.Models.Candles.Candle;
import com.eduard.diploma.trader.Models.Candles.OneMinuteCandle;
import com.eduard.diploma.trader.Test2.BinanceData;

import java.text.SimpleDateFormat;
import java.util.*;

public class Main7 {
    public static void main(String[] args) {
        List<OneMinuteCandle> list =
                BinanceData.getBinanceData_1V2(1653036301000L).stream()
                        .map(candlestick -> new OneMinuteCandle(
                                null, CurrenciesPairs.BTCBUSD,
                                candlestick.getOpenTime(), processingDate(candlestick.getOpenTime()), candlestick.getOpen(),
                                candlestick.getCloseTime(), processingDate(candlestick.getCloseTime()), candlestick.getClose(),
                                candlestick.getHigh(), candlestick.getLow()
                        ))
                        .toList();

        Finder finder = new Finder();

        Map<String, List<? extends Candle>> map = finder.search(list);
        System.out.println(finder.search(list));

        if (map.get("Minimums").size() >= 2 && map.get("Maximums").size() >= 2){

            Handler handler = new Handler();
            for (Map.Entry<String, List<? extends Candle>> mapCurrent : map.entrySet()){
                if (mapCurrent.getKey().equals("Maximums")){
                    map.replace("Maximums", handler.processingPoints(mapCurrent.getValue(), mapCurrent.getKey()));
                }
                else if (mapCurrent.getKey().equals("Minimums")){
                    map.replace("Minimums", handler.processingPoints(mapCurrent.getValue(), mapCurrent.getKey()));
                }
            }

            System.out.println();
            System.out.println("handler.processingPoints(...)");
            System.out.println("map: " + "\n");
            System.out.println(map + "\n");

            //deleteDuplicate
            for (Map.Entry<String, List<? extends Candle>> mapCurrent : map.entrySet()){
                if (mapCurrent.getKey().equals("Maximums") || mapCurrent.getKey().equals("Minimums")){
                    for (int i = 1; i < mapCurrent.getValue().size(); i++){
                        if (mapCurrent.getValue().get(i).getCloseTime().equals(mapCurrent.getValue().get(i - 1).getCloseTime())){
                            mapCurrent.getValue().remove(i);
                        }
                    }
                }
            }

            System.out.println();
            System.out.println("handler.deleteDuplicate(...)");
            System.out.println("map: " + "\n");
            System.out.println(map + "\n");


            Merge merge = new Merge();
            Map<String, List<? extends Candle>> mapLast = merge.mergeExtremesV2(map);


            System.out.println();
            System.out.println("handler.merge(...)");
            System.out.println("map: " + "\n");
            System.out.println(mapLast + "\n");

            

        }


    }

    private static String processingDate(Long date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        return simpleDateFormat.format(new Date(date));
    }

}
