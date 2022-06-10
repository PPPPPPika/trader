package com.eduard.diploma.trader.Test2;

import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.eduard.diploma.trader.Adapter.Configuration.ClientConfiguration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BinanceData {
    private Long openTime;
    private String openPrice;
    private Long closeTime;
    private String closePrice;

    private String maxPrice;
    private String minPrice;

    public String processingDate(Long date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        return simpleDateFormat.format(new Date(date));
    }

    @Override
    public String toString() {
        return "InformationRecipient{" + "\n" +
                "Open time: " + processingDate(openTime) + "\n" +
                "Open price: " + openPrice + "\n" +
                "Close time: " + processingDate(closeTime) + "\n" +
                "Close price: " + closePrice + "\n" +
                "Max price: " + maxPrice + "\n" +
                "Min price: " + minPrice + "\n" +
                "}" + "\n";
    }

    public static List<Candlestick> getBinanceData_1(Long startTime) {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        List<Candlestick> listCandlestick = clientConfiguration.getClient()
                .getCandlestickBars("BTCBUSD", CandlestickInterval.ONE_MINUTE, 1000, startTime, startTime + 60000000);
        listCandlestick.addAll(clientConfiguration.getClient()
                .getCandlestickBars("BTCBUSD", CandlestickInterval.ONE_MINUTE, 440, startTime + 60000000, startTime + 86400000));
        return listCandlestick;
    }

    public static List<Candlestick> getBinanceData_1V2(Long endTime) {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        return clientConfiguration.getClient()
                .getCandlestickBars("BTCBUSD", CandlestickInterval.ONE_MINUTE, 90, endTime - 16200000, endTime);
    }

    public static List<Candlestick> getBinanceData_5(Long startTime) {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        return clientConfiguration.getClient()
                .getCandlestickBars("BTCBUSD", CandlestickInterval.FIVE_MINUTES, 288, startTime, startTime + 86400000);
    }

    public static List<Candlestick> getBinanceData_5_36(Long startTime) {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        return clientConfiguration.getClient()
                .getCandlestickBars("BTCBUSD", CandlestickInterval.FIVE_MINUTES, 36, startTime, startTime + 10800000);
    }

    public static List<Candlestick> getBinanceData_15(Long startTime) {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        return clientConfiguration.getClient()
                .getCandlestickBars("BTCBUSD", CandlestickInterval.ONE_MINUTE, 96, startTime, startTime + 86400000);
    }

    public static List<Candlestick> getBinanceData_30(Long startTime) {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        return clientConfiguration.getClient()
                .getCandlestickBars("BTCBUSD", CandlestickInterval.ONE_MINUTE, 48, startTime, startTime + 86400000);
    }

    public static List<Candlestick> getBinanceData_60(Long startTime) {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        return clientConfiguration.getClient()
                .getCandlestickBars("BTCBUSD", CandlestickInterval.ONE_MINUTE, 24, startTime, startTime + 86400000);
    }

    public static List<Candlestick> getBinanceData_180(Long startTime) {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        return clientConfiguration.getClient()
                .getCandlestickBars("BTCBUSD", CandlestickInterval.ONE_MINUTE, 8, startTime, startTime + 86400000);
    }

    public static List<Candlestick> getBinanceData_360(Long startTime) {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        return clientConfiguration.getClient()
                .getCandlestickBars("BTCBUSD", CandlestickInterval.ONE_MINUTE, 6, startTime, startTime + 86400000);
    }

    public static List<Candlestick> getBinanceData_1440(Long startTime) {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        return clientConfiguration.getClient()
                .getCandlestickBars("BTCBUSD", CandlestickInterval.DAILY, 1000, startTime, new Date().getTime());
    }

    public static List<Candlestick> getBinanceData_60_7(Long startTime) {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        return clientConfiguration.getClient()
                .getCandlestickBars("BTCBUSD", CandlestickInterval.HOURLY, 168, startTime, startTime + 518400000);
    }

    public static List<Candlestick> getBinanceData_60_3(Long startTime) {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        return clientConfiguration.getClient()
                .getCandlestickBars("BTCBUSD", CandlestickInterval.HOURLY, 72, startTime, startTime + 259200000);
    }


}
