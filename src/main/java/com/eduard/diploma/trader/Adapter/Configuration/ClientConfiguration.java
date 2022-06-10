package com.eduard.diploma.trader.Adapter.Configuration;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
    @Value(value = "${binance.api.key}")
    private String binanceApiKey;

    @Value(value = "${binance.api.secret}")
    private String binanceApiSecret;

    public BinanceApiRestClient getClient(){
        return BinanceApiClientFactory.newInstance(binanceApiKey, binanceApiSecret).newRestClient();
    }
}
