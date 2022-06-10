package com.eduard.diploma.trader.Services;

import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import com.eduard.diploma.trader.Models.Candles.Candle;
import com.eduard.diploma.trader.Models.Candles.Enums.KindsCandles;
import com.eduard.diploma.trader.Services.Receiver.PackageCandles;

import java.util.List;

public interface CandlesProcessingService {
    String processingDate(Long date);

    Object selectionCandleType(Candlestick candlestick, CandlestickInterval candlestickInterval, CurrenciesPairs currenciesPairs);

    <CandleType extends Candle> boolean isPositiveCandle(CandleType candle);

    KindsCandles determineTypeCandles(List<? extends Candle> list);

    List<KindsCandles> unpackCandlesPackage(PackageCandles packageCandles);
}
