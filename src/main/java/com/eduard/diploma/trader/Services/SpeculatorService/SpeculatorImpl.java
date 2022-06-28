package com.eduard.diploma.trader.Services.SpeculatorService;

import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import com.eduard.diploma.trader.Adapter.Market.PriceExplorer;
import com.eduard.diploma.trader.Adapter.Market.PriceExplorerImpl;
import com.eduard.diploma.trader.Models.Candles.Enums.KindsCandles;
import com.eduard.diploma.trader.Services.Receiver.*;
import com.eduard.diploma.trader.Services.SpeculatorService.AnalysisResultProcessing.TradingConditionsResearcherImpl;
import com.eduard.diploma.trader.Services.SpeculatorService.Details.CurrentSituationDetails;
import com.eduard.diploma.trader.Services.SpeculatorService.Exceptions.WaitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

@Service
public class SpeculatorImpl implements Speculator{
    private final TradingConditionsResearcherImpl tradingConditionsResearcherImpl;
    private final PriceExplorer priceExplorer;
    private final ReceiverLaunchService receiverLaunchService;
    //private final TimeManager timeManager;

    //private int usedMinute = 0;

    @Autowired
    public SpeculatorImpl(TradingConditionsResearcherImpl tradingConditionsResearcherImpl,
                          PriceExplorerImpl priceExplorerImpl,
                          ReceiverLaunchServiceImplAlternative receiverLaunchServiceImpl
                          /*TimeManager timeManager*/){
        this.tradingConditionsResearcherImpl = tradingConditionsResearcherImpl;
        this.priceExplorer = priceExplorerImpl;
        this.receiverLaunchService = receiverLaunchServiceImpl;
        //this.timeManager = timeManager;
    }

    public Flux<CurrentSituationDetails> startTrade(CurrenciesPairs currenciesPairs){
        return trade(currenciesPairs)
                .repeat(1000);
    }

    @Override
    public Flux<CurrentSituationDetails> trade(CurrenciesPairs currenciesPairs){
        return Flux.just("")
                .flatMap(emptyValue -> tradingConditionsResearcherImpl.research(currenciesPairs))
                .flatMap(currentSituationDetails -> waitingToBuy(currenciesPairs, currentSituationDetails))
                .filter(value -> !value.isEmpty())
                .flatMap(currentSituationDetails -> waitingToSell(currenciesPairs, currentSituationDetails));
    }

    private Flux<CurrentSituationDetails> waitingToBuy(CurrenciesPairs currenciesPairs, CurrentSituationDetails currentSituationDetails){
        return Flux.just("")
                .flatMap(emptyValue -> receiverLaunchService.active(currenciesPairs))
                .onErrorReturn(new PackageCandles())
                .flatMap(someValue -> priceExplorer.getValueCurrencyPairs(currenciesPairs))
                .map(price -> {
                    System.out.println("Waiting to buy..." + "\n" + "Middle price: " + currentSituationDetails.getMiddles().get("finalMiddle") + "\n");
                    double currentPrice = Double.parseDouble(price);

                    double highBorderBuy = new BigDecimal(currentSituationDetails.getTopTradingRange()).doubleValue();
                    double lowBorderBuy = new BigDecimal(currentSituationDetails.getBottomTradingRange()).doubleValue();

                    if (currentPrice <= Double.parseDouble(currentSituationDetails.getExtremes().get(currentSituationDetails.getExtremes().size() - 1).getMaxPrice())){
                        if (currentPrice <= highBorderBuy && currentPrice >= lowBorderBuy){
                            System.out.println(
                                    "\n" + "___BUY___" +
                                    "\n" + "Candle: " + currentSituationDetails.getKindCandle() +
                                    "\n" + "Date: " + new Date() +
                                    "\n" + "Current price: " + price +
                                    "\n"
                            );
                            return currentSituationDetails;
                        }
                        else
                            throw new WaitException("Wait to buy...");
                    }
                    else
                        return new CurrentSituationDetails();
                })
                .retryWhen(Retry.fixedDelay(Long.MAX_VALUE, Duration.ofSeconds(1)));
    }

    private Flux<CurrentSituationDetails> waitingToSell(CurrenciesPairs currenciesPairs, CurrentSituationDetails currentSituationDetails){
        return Flux.just("")
                .flatMap(emptyValue -> receiverLaunchService.active(currenciesPairs))
                .onErrorReturn(new PackageCandles())
                .flatMap(emptyValue -> priceExplorer.getValueCurrencyPairs(currenciesPairs))
                .map(price -> {
                    double currentPrice = Double.parseDouble(price);

                    double priceToSell = Double.parseDouble(currentSituationDetails.getExtremes().get(currentSituationDetails.getExtremes().size() - 1).getMaxPrice());
                    double lowBorderSell = Double.parseDouble(currentSituationDetails.getBottomTradingRange());

                    System.out.println("Waiting to sell..." + "\n" + "Middle price: " + currentSituationDetails.getMiddles().get("finalMiddle") + "\n" +
                            "lowBorderSell: " + lowBorderSell);

                    if (currentPrice >= priceToSell || currentPrice <= lowBorderSell){
                        System.out.println(
                                "\n" + "___SELL___" +
                                "\n" + "Candle: " + currentSituationDetails.getKindCandle() +
                                "\n" + "Date: " + new Date() +
                                "\n" + "Current price: " + price +
                                "\n"
                        );
                        return currentSituationDetails;
                    }
                    else
                        throw new WaitException("Wait to sell...");
                })
                .retryWhen(Retry.fixedDelay(Long.MAX_VALUE, Duration.ofSeconds(1)));
    }

}
