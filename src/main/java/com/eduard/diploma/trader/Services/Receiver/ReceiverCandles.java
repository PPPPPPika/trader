package com.eduard.diploma.trader.Services.Receiver;

import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import com.eduard.diploma.trader.Models.Candles.Candle;
import com.eduard.diploma.trader.Models.Candles.Enums.KindsCandles;
import reactor.core.publisher.Flux;

public interface ReceiverCandles {
    Flux<Object> receive(CurrenciesPairs currenciesPairs, KindsCandles kindsCandles);

    default Flux<? extends Candle> receivePreviousCandles(CurrenciesPairs currenciesPairs, KindsCandles kindsCandles) {
        return null;
    }
}
