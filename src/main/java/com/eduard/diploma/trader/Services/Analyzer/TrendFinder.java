package com.eduard.diploma.trader.Services.Analyzer;

import com.eduard.diploma.trader.Models.Candles.Candle;

import java.util.List;
import java.util.Map;

public interface TrendFinder {
    Map<String, List<? extends Candle>> search(List<? extends Candle> candlesList);
}
