package com.eduard.diploma.trader.Services.SpeculatorService;

import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import com.eduard.diploma.trader.Adapter.Market.PriceExplorer;
import com.eduard.diploma.trader.Adapter.Market.PriceExplorerImpl;
import com.eduard.diploma.trader.Models.Candles.Enums.KindsCandles;
import com.eduard.diploma.trader.Services.Receiver.PackageCandles;
import com.eduard.diploma.trader.Services.Receiver.ReceiverLaunchServiceImpl;
import com.eduard.diploma.trader.Services.Receiver.TimeManager;
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
    private final ReceiverLaunchServiceImpl receiverLaunchService;
    private final TimeManager timeManager;

    private int usedMinute = 0;

    @Autowired
    public SpeculatorImpl(TradingConditionsResearcherImpl tradingConditionsResearcherImpl,
                          PriceExplorerImpl priceExplorerImpl,
                          ReceiverLaunchServiceImpl receiverLaunchServiceImpl,
                          TimeManager timeManager){
        this.tradingConditionsResearcherImpl = tradingConditionsResearcherImpl;
        this.priceExplorer = priceExplorerImpl;
        this.receiverLaunchService = receiverLaunchServiceImpl;
        this.timeManager = timeManager;
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

    private Flux<PackageCandles> alternativeReceiveCandles(CurrenciesPairs currenciesPairs, GregorianCalendar gregorianCalendar){
        return Flux.just("")
                .flatMap(emptyValue -> {
                    usedMinute = gregorianCalendar.get(Calendar.MINUTE);

                    List<KindsCandles> listCurrentCandles = new LinkedList<>(List.of(KindsCandles.ONE));
                    listCurrentCandles.addAll(
                            Arrays.stream(timeManager.identifyCurrentCandles(gregorianCalendar.get(Calendar.MINUTE)))
                                    .filter(Objects::nonNull)
                                    .toList()
                    );
                    listCurrentCandles =
                            listCurrentCandles.stream()
                                    .sorted(Comparator.comparing(KindsCandles::getDurationInterval))
                                    .toList();
                    return receiverLaunchService.getCandles(currenciesPairs, listCurrentCandles);
                });
    }

    private Flux<CurrentSituationDetails> waitingToBuy(CurrenciesPairs currenciesPairs, CurrentSituationDetails currentSituationDetails){
        return Flux.just("")
                .flatMap(emptyValue -> {
                    GregorianCalendar calendar = new GregorianCalendar();
                    if (calendar.get(GregorianCalendar.SECOND) == 0)
                        return alternativeReceiveCandles(currenciesPairs, calendar);
                    else if (usedMinute != calendar.get(Calendar.MINUTE) && calendar.get(GregorianCalendar.SECOND) == 1)
                        return alternativeReceiveCandles(currenciesPairs, calendar);
                    else if (usedMinute != calendar.get(Calendar.MINUTE) && calendar.get(GregorianCalendar.SECOND) == 2)
                        return alternativeReceiveCandles(currenciesPairs, calendar);
                    else
                        return Flux.error(new IllegalArgumentException("Non time"));
                })
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
                            throw new WaitException("Wait start...");
                    }
                    else
                        return new CurrentSituationDetails();
                })
                .retryWhen(Retry.fixedDelay(Long.MAX_VALUE, Duration.ofSeconds(1)));
    }

    private Flux<CurrentSituationDetails> waitingToSell(CurrenciesPairs currenciesPairs, CurrentSituationDetails currentSituationDetails){
        return Flux.just("")
                .flatMap(emptyValue -> {
                    GregorianCalendar calendar = new GregorianCalendar();
                    if (calendar.get(GregorianCalendar.SECOND) == 0)
                        return alternativeReceiveCandles(currenciesPairs, calendar);
                    else if (usedMinute != calendar.get(Calendar.MINUTE) && calendar.get(GregorianCalendar.SECOND) == 1)
                        return alternativeReceiveCandles(currenciesPairs, calendar);
                    else if (usedMinute != calendar.get(Calendar.MINUTE) && calendar.get(GregorianCalendar.SECOND) == 2)
                        return alternativeReceiveCandles(currenciesPairs, calendar);
                    else
                        return Flux.error(new IllegalArgumentException("Non time"));
                })
                .onErrorReturn(new PackageCandles())
                .flatMap(emptyValue -> priceExplorer.getValueCurrencyPairs(currenciesPairs))
                .map(price -> {
                    double currentPrice = Double.parseDouble(price);

                    double priceToSell = Double.parseDouble(currentSituationDetails.getExtremes().get(currentSituationDetails.getExtremes().size() - 1).getMaxPrice());
                    double lowBorderSell = Double.parseDouble(currentSituationDetails.getBottomTradingRange());

                    System.out.println("Waiting to buy..." + "\n" + "Middle price: " + currentSituationDetails.getMiddles().get("finalMiddle") + "\n" +
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
                        throw new WaitException("Wait sell...");
                })
                .retryWhen(Retry.fixedDelay(Long.MAX_VALUE, Duration.ofSeconds(1)));
    }

}
