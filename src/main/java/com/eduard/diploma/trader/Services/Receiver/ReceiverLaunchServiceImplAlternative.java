package com.eduard.diploma.trader.Services.Receiver;

import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import com.eduard.diploma.trader.Services.Receiver.Exceptions.WrongTimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Service
public class ReceiverLaunchServiceImplAlternative implements ReceiverLaunchService{
    private final ReceiverLaunchServiceImpl receiverLaunchService;
    private final TimeManager timeManager;

    private int usedMinute = 0;

    @Autowired
    public ReceiverLaunchServiceImplAlternative(ReceiverLaunchServiceImpl receiverLaunchService, TimeManager timeManager) {
        this.receiverLaunchService = receiverLaunchService;
        this.timeManager = timeManager;
    }

    @Override
    public Flux<PackageCandles> active(CurrenciesPairs currenciesPairs) {
        return Flux.just("")
                .flatMap(emptyValue -> {
                    GregorianCalendar calendar = new GregorianCalendar();

                    int currentSecond = calendar.get(GregorianCalendar.SECOND);
                    int currentMinutes = calendar.get(Calendar.MINUTE);

                    if (currentSecond == 0)
                        return alternativeReceiveCandles(currenciesPairs, calendar);
                    else if (usedMinute != currentMinutes && currentSecond == 1)
                        return alternativeReceiveCandles(currenciesPairs, calendar);
                    else if (usedMinute != currentMinutes && currentSecond == 2)
                        return alternativeReceiveCandles(currenciesPairs, calendar);
                    else
                        return Flux.error(
                                new WrongTimeException(
                                        """
                                        Class: ReceiverLaunchServiceImplAlternative
                                        Method: Flux<Object> active(CurrenciesPairs currenciesPairs)
                                        """
                                )
                        );
                })
                .onErrorReturn(new PackageCandles());
    }

    private Flux<PackageCandles> alternativeReceiveCandles(CurrenciesPairs currenciesPairs, GregorianCalendar gregorianCalendar){
        return Flux.just("")
                .flatMap(emptyValue -> {
                    usedMinute = gregorianCalendar.get(Calendar.MINUTE);
                    return receiverLaunchService.getCandles(currenciesPairs, timeManager.getCurrentCandles(gregorianCalendar));
                });
    }

}
