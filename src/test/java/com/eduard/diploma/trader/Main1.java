package com.eduard.diploma.trader;

import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.eduard.diploma.trader.Adapter.Configuration.ClientConfiguration;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main1 {

    static String str1 = "1646690999999";
    static String str2 = "1646691059999";
    static String str3 = "1646691119999";
    static String str4 = "1646691179999";
    static String str5 = "1646691239999";
    static String str6 = "1646691299999";
    static String str7 = "1646691359999";

    /*static Long date28 = 1646006400000L; //февраль - март
    static Long date1 = 1646092800000L;
    static Long date2 = 1646179200000L;
    static Long date3 = 1646265600000L;
    static Long date4 = 1646352000000L;
    static Long date5 = 1646438400000L;
    static Long date6 = 1646524800000L;
    static Long date7 = 1646611200000L;
    static Long date8 = 1646697600000L;
    static Long date9 = 1646784000000L;
    static Long date10 = 1646870400000L;
    static Long date10f = 1644451200000L;
    static Long date11 = 1644537600000L;
    static Long date12 = 1644624000000L;
    static Long date13 = 1644710400000L;
    static Long date14 = 1644796800000L;
    static Long date15 = 1644883200000L;
    static Long date16 = 1644969600000L;
    static Long date17 = 1645056000000L;
    static Long date18 = 1645142400000L;
    static Long date19 = 1645228800000L;
    static Long date20 = 1645315200000L;
    static Long date21 = 1645401600000L;
    static Long date22 = 1645488000000L;
    static Long date23 = 1645574400000L;
    static Long date24 = 1645660800000L;
    static Long date25 = 1645747200000L;
    static Long date26 = 1645833600000L;
    static Long date27 = 1645920000000L;*/


    /*static Long date10 = 1639094400000L; //декабрь - январь
    static Long date11 = 1639180800000L;
    static Long date12 = 1639267200000L;
    static Long date13 = 1639353600000L;
    static Long date14 = 1639440000000L;
    static Long date15 = 1639526400000L;
    static Long date16 = 1639612800000L;
    static Long date17 = 1639699200000L;
    static Long date18 = 1639785600000L;
    static Long date19 = 1639872000000L;
    static Long date20 = 1639958400000L;
    static Long date21 = 1640044800000L;
    static Long date22 = 1640131200000L;
    static Long date23 = 1640217600000L;
    static Long date24 = 1640304000000L;
    static Long date25 = 1640390400000L;
    static Long date26 = 1640476800000L;
    static Long date27 = 1640563200000L;
    static Long date28 = 1640649600000L;
    static Long date29 = 1640736000000L;
    static Long date30 = 1640822400000L;
    static Long date31 = 1640908800000L;
    static Long date1 = 1640995200000L;
    static Long date2 = 1641081600000L;
    static Long date3 = 1641168000000L;
    static Long date4 = 1641254400000L;
    static Long date5 = 1641340800000L;
    static Long date6 = 1641427200000L;
    static Long date7 = 1641513600000L;
    static Long date8 = 1641600000000L;
    static Long date9 = 1641686400000L;
    static Long date10f = 1641772800000L;*/




    static Long date10 =  1641772800000L;
    static Long date11 =  1641859200000L;
    static Long date12 =  1641945600000L;
    static Long date13 =  1642032000000L;
    static Long date14 =  1642118400000L;
    static Long date15 =  1642204800000L;
    static Long date16 =  1642291200000L;
    static Long date17 =  1642377600000L;
    static Long date18 =  1642464000000L;
    static Long date19 =  1642550400000L;
    static Long date20 =  1642636800000L;
    static Long date21 =  1642723200000L;
    static Long date22 =  1642809600000L;
    static Long date23 =  1642896000000L;
    static Long date24 =  1642982400000L;
    static Long date25 =  1643068800000L;
    static Long date26 =  1643155200000L;
    static Long date27 =  1643241600000L;
    static Long date28 =  1643328000000L;
    static Long date29 =  1643414400000L;
    static Long date30 =  1643500800000L;
    static Long date31 =  1643587200000L;
    static Long date1 =  1643673600000L;
    static Long date2 =  1643760000000L;
    static Long date3 =  1643846400000L;
    static Long date4 =  1643932800000L;
    static Long date5 =  1644019200000L;
    static Long date6 =  1644105600000L;
    static Long date7 =  1644192000000L;
    static Long date8 =  1644278400000L;
    static Long date9 =  1644364800000L;
    static Long date10f =  1644451200000L;







    public static void main(String[] args) throws ParseException {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        /*List<Candlestick> list = clientConfiguration.getClient()
                .getCandlestickBars("BTCBUSD", CandlestickInterval.ONE_MINUTE, 1000, 1646006400000L, 1646940097000L);

        List<InformationRecipient> informationRecipientList =
                list.stream()
                        .map(candlestick -> new InformationRecipient(candlestick.getOpenTime(), candlestick.getOpen(),
                                candlestick.getCloseTime(), candlestick.getClose()))
                        .toList();

        DataProcessor dataProcessor = new DataProcessor();
        System.out.println(dataProcessor.algorithm(informationRecipientList));*/


        //List<Long> longList = new ArrayList<>(List.of(date10f, date11, date12, date13, date14, date15, date16, date17, date18, date19, date20, date21, date22, date23, date24, date25, date26, date27, date28, date1, date2, date3, date4, date5, date6, date7, date8, date9, date10));
        //List<Long> longList = new ArrayList<>(List.of(date10, date11, date12, date13, date14, date15, date16, date17, date18, date19, date20, date21, date22, date23, date24, date25, date26, date27, date28, date29, date30, date31, date1, date2, date3, date4, date5, date6, date7, date8, date9, date10f));
        List<Long> longList = new ArrayList<>(List.of(date10, date11, date12, date13, date14, date15, date16, date17, date18, date19, date20, date21, date22, date23, date24, date25, date26, date27, date28, date29, date30, date31, date1, date2, date3, date4, date5, date6, date7, date8, date9, date10f));


        List<DataProcessor.TransformData> resultList = new ArrayList<>();

        resultList = longList.stream().map(date ->{
            List<Candlestick> list = clientConfiguration.getClient()
                    .getCandlestickBars("BTCBUSD", CandlestickInterval.ONE_MINUTE, 1000, date, 1646940097000L);
            List<InformationRecipient> informationRecipientList =
                    list.stream()
                            .map(candlestick -> new InformationRecipient(candlestick.getOpenTime(), candlestick.getOpen(),
                                    candlestick.getCloseTime(), candlestick.getClose()))
                            .toList();
            DataProcessor dataProcessor = new DataProcessor();
            return dataProcessor.algorithm(informationRecipientList);
        })
                .toList();

        resultList.forEach(System.out::println);

        Optional<BigDecimal> result = resultList.stream()
                .map(value -> new BigDecimal(value.getClosePrice()).subtract(new BigDecimal(value.getOpenPrice())))
                .reduce(BigDecimal::add);

        System.out.println(result.get());


    }
}
