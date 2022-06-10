package com.eduard.diploma.trader.Controllers;

//import com.eduard.diploma.trader.Services.Analysis.CandleAnalyzer;
import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import com.eduard.diploma.trader.Services.Analyzer.TrendAnalyzerImpl;
import com.eduard.diploma.trader.Services.CandlesServiceImpl;
import com.eduard.diploma.trader.Services.Receiver.ReceiverCandlesImpl;
import com.eduard.diploma.trader.Services.Receiver.ReceiverLaunchServiceImpl;
import com.eduard.diploma.trader.Services.SpeculatorService.Details.CurrentSituationDetails;
import com.eduard.diploma.trader.Services.SpeculatorService.AnalysisResultProcessing.ExplorerExtremesImpl;
import com.eduard.diploma.trader.Services.SpeculatorService.SpeculatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("")
public class ControllerTest {
    private final ReceiverCandlesImpl receiverCandlesImpl;
    private final CandlesServiceImpl candlesServiceImpl;
    private final ReceiverLaunchServiceImpl receiverLaunchServiceImpl;
    private final TrendAnalyzerImpl trendAnalyzerImpl;
    private final ExplorerExtremesImpl explorerExtremesImpl;
    private final SpeculatorImpl speculatorImpl;

    //private final CandleAnalyzer candleAnalyzer;

    @Autowired
    public ControllerTest(ReceiverCandlesImpl receiverCandlesImpl,/*, CandleAnalyzer candleAnalyzer*/CandlesServiceImpl candlesServiceImpl,
                          ReceiverLaunchServiceImpl receiverLaunchServiceImpl, TrendAnalyzerImpl trendAnalyzerImpl,
                          ExplorerExtremesImpl explorerExtremesImpl, SpeculatorImpl speculatorImpl) {
        this.receiverCandlesImpl = receiverCandlesImpl;
        //this.candleAnalyzer = candleAnalyzer;
        this.candlesServiceImpl = candlesServiceImpl;
        this.receiverLaunchServiceImpl = receiverLaunchServiceImpl;
        this.trendAnalyzerImpl = trendAnalyzerImpl;
        this.explorerExtremesImpl = explorerExtremesImpl;
        this.speculatorImpl = speculatorImpl;
    }

   /* @GetMapping("/reciever")
    public Mono<Candlestick> receiverTest(){
        return receiverData.getCandleStick(CurrenciesPairs.BTCBUSD);
    }*/

    /*@GetMapping("/reciever")
    public Flux<CandleReport> receiverTest1(){
        //return receiverData.getCandle(CurrenciesPairs.BTCBUSD, CandlestickInterval.HOURLY);
        return receiverData.start(CurrenciesPairs.BTCBUSD);
    }*/

    /*@GetMapping("/recievePrevious")
    public Flux<? extends Candle> receiverTest1(){
        //return receiverData.getCandle(CurrenciesPairs.BTCBUSD, CandlestickInterval.HOURLY);
        return receiverData.getPreviousCandles(CurrenciesPairs.BTCBUSD, KindsCandles.ONE);
    }*/

    /*@GetMapping("/analyze")
    public Flux<List<? extends Candle>> receiverTest(){
        //return receiverData.getCandle(CurrenciesPairs.BTCBUSD, CandlestickInterval.HOURLY);
        return candleAnalyzer.startAnalyzing(CurrenciesPairs.BTCBUSD);
    }*/

    /*@GetMapping("/recieveF")
    public Flux<PackageCandles> receiverTest2(){
        //return receiverData.getCandle(CurrenciesPairs.BTCBUSD, CandlestickInterval.HOURLY);
        return receiverLaunchServiceImpl.start(CurrenciesPairs.BTCBUSD);
    }*/

    /*@GetMapping("/filter")
    public Mono<Boolean> filter(){
        return candlesServiceImpl.isDuplicateCandle(new OneMinuteCandle());
    }*/

    /*@GetMapping("/checkDup")
    public Mono<Object> checkDup(){
        return receiverData
                .checkDuplicate(
                        new OneCandle("62640bc6f6b0086e87a8d5b4", CurrenciesPairs.BTCBUSD,
                                1650723660000L, "2022-04-23 17:21:00", "39713.28000000",
                                1650723719999L, "2022-04-23 17:21:59", "39701.54000000",
                                "39713.28000000", "39701.54000000")
                )
                .retryWhen(Retry.fixedDelay(10, Duration.ofMillis(500)));
    }*/

    /*@GetMapping("/analyze")
    public Flux<Map<KindsCandles, List<? extends Candle>>> analyze(){
        return trendAnalyzerImpl.start(CurrenciesPairs.BTCBUSD);
    }*/


    /*@GetMapping("/exp")
    public Flux<Map<KindsCandles, List<? extends Candle>>> explore(){
        return explorerExtremesImpl.startWarden(CurrenciesPairs.BTCBUSD);
    }*/

    @GetMapping("/trade")
    public Flux<CurrentSituationDetails> trade(){
        return speculatorImpl.startTrade(CurrenciesPairs.BTCBUSD);
    }


}
