package com.eduard.diploma.trader.Services;

import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import com.eduard.diploma.trader.Models.Candles.*;
import com.eduard.diploma.trader.Models.Candles.Enums.KindsCandles;
import com.eduard.diploma.trader.Repositorys.CandlesRepositorys.*;
import com.eduard.diploma.trader.Services.Exceptions.WrongCandleException;
import com.eduard.diploma.trader.Services.Exceptions.WrongCandleIntervalException;
import com.eduard.diploma.trader.Services.Receiver.PackageCandles;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CandlesServiceImpl implements CandlesService, CandlesProcessingService {
    private final OneMinuteCandleRepository oneMinuteCandleRepository;
    private final ThreeMinuteCandleRepository threeMinuteCandleRepository;
    private final FiveMinuteCandleRepository fiveMinuteCandleRepository;
    private final FifteenMinuteCandleRepository fifteenMinuteCandleRepository;
    private final ThirtyMinuteCandleRepository thirtyMinuteCandleRepository;
    private final SixtyMinuteCandleRepository sixtyMinuteCandleRepository;

    public CandlesServiceImpl(OneMinuteCandleRepository oneMinuteCandleRepository,
                              ThreeMinuteCandleRepository threeMinuteCandleRepository,
                              FiveMinuteCandleRepository fiveMinuteCandleRepository,
                              FifteenMinuteCandleRepository fifteenMinuteCandleRepository,
                              ThirtyMinuteCandleRepository thirtyMinuteCandleRepository,
                              SixtyMinuteCandleRepository sixtyMinuteCandleRepository) {
        this.oneMinuteCandleRepository = oneMinuteCandleRepository;
        this.threeMinuteCandleRepository = threeMinuteCandleRepository;
        this.fiveMinuteCandleRepository = fiveMinuteCandleRepository;
        this.fifteenMinuteCandleRepository = fifteenMinuteCandleRepository;
        this.thirtyMinuteCandleRepository = thirtyMinuteCandleRepository;
        this.sixtyMinuteCandleRepository = sixtyMinuteCandleRepository;
    }

    @Override
    public String processingDate(Long date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        return simpleDateFormat.format(new Date(date));
    }

    @Override
    public <CandleType extends Candle> boolean isPositiveCandle(CandleType candle){
        return new BigDecimal(candle.getClosePrice()).subtract(new BigDecimal(candle.getOpenPrice())).doubleValue() > 0;
    }

    @Override
    public Object selectionCandleType(Candlestick candlestick, CandlestickInterval candlestickInterval, CurrenciesPairs currenciesPairs){
        return switch (candlestickInterval) {
            case ONE_MINUTE -> new OneMinuteCandle(null, currenciesPairs,
                    candlestick.getOpenTime(), processingDate(candlestick.getOpenTime()), candlestick.getOpen(),
                    candlestick.getCloseTime(), processingDate(candlestick.getCloseTime()), candlestick.getClose(),
                    candlestick.getHigh(), candlestick.getLow());
            case THREE_MINUTES -> new ThreeMinuteCandle(null, currenciesPairs,
                    candlestick.getOpenTime(), processingDate(candlestick.getOpenTime()), candlestick.getOpen(),
                    candlestick.getCloseTime(), processingDate(candlestick.getCloseTime()), candlestick.getClose(),
                    candlestick.getHigh(), candlestick.getLow());
            case FIVE_MINUTES -> new FiveMinuteCandle(null, currenciesPairs,
                    candlestick.getOpenTime(), processingDate(candlestick.getOpenTime()), candlestick.getOpen(),
                    candlestick.getCloseTime(), processingDate(candlestick.getCloseTime()), candlestick.getClose(),
                    candlestick.getHigh(), candlestick.getLow());
            case FIFTEEN_MINUTES -> new FifteenMinuteCandle(null, currenciesPairs,
                    candlestick.getOpenTime(), processingDate(candlestick.getOpenTime()), candlestick.getOpen(),
                    candlestick.getCloseTime(), processingDate(candlestick.getCloseTime()), candlestick.getClose(),
                    candlestick.getHigh(), candlestick.getLow());
            case HALF_HOURLY -> new ThirtyMinuteCandle(null, currenciesPairs,
                    candlestick.getOpenTime(), processingDate(candlestick.getOpenTime()), candlestick.getOpen(),
                    candlestick.getCloseTime(), processingDate(candlestick.getCloseTime()), candlestick.getClose(),
                    candlestick.getHigh(), candlestick.getLow());
            case HOURLY -> new SixtyMinuteCandle(null, currenciesPairs,
                    candlestick.getOpenTime(), processingDate(candlestick.getOpenTime()), candlestick.getOpen(),
                    candlestick.getCloseTime(), processingDate(candlestick.getCloseTime()), candlestick.getClose(),
                    candlestick.getHigh(), candlestick.getLow());
            default -> throw new WrongCandleIntervalException(
                            """
                            Class: CandlesServiceImpl
                            Method: Object selectionCandleType(Candlestick candlestick, CandlestickInterval candlestickInterval, CurrenciesPairs currenciesPairs)
                            """
            );
        };
    }

    @Override
    public <CandleType extends Candle> Flux<? extends Candle> saveManyCandles(List<CandleType> listCandles){
        if (!listCandles.isEmpty() && listCandles.get(0) instanceof OneMinuteCandle)
            return oneMinuteCandleRepository.saveAll((List<OneMinuteCandle>) listCandles);
        else if (!listCandles.isEmpty() && listCandles.get(0) instanceof ThreeMinuteCandle)
            return threeMinuteCandleRepository.saveAll((List<ThreeMinuteCandle>) listCandles);
        else if (!listCandles.isEmpty() && listCandles.get(0) instanceof FiveMinuteCandle)
            return fiveMinuteCandleRepository.saveAll((List<FiveMinuteCandle>) listCandles);
        else if (!listCandles.isEmpty() && listCandles.get(0) instanceof FifteenMinuteCandle)
            return fifteenMinuteCandleRepository.saveAll((List<FifteenMinuteCandle>) listCandles);
        else if (!listCandles.isEmpty() && listCandles.get(0) instanceof ThirtyMinuteCandle)
            return thirtyMinuteCandleRepository.saveAll((List<ThirtyMinuteCandle>) listCandles);
        else if (!listCandles.isEmpty() && listCandles.get(0) instanceof SixtyMinuteCandle)
            return sixtyMinuteCandleRepository.saveAll((List<SixtyMinuteCandle>) listCandles);
        else
            throw new WrongCandleException(
                    """
                    Class: CandlesServiceImpl
                    Method: <CandleType extends Candle> Flux<? extends Candle> saveManyCandles
                    """
            );
    }

    @Override
    public Mono<?> saveCandle(Object candle){
        if (candle instanceof OneMinuteCandle instance)
            return oneMinuteCandleRepository.save(instance);
        else if (candle instanceof ThreeMinuteCandle instance)
            return threeMinuteCandleRepository.save(instance);
        else if (candle instanceof FiveMinuteCandle instance)
            return fiveMinuteCandleRepository.save(instance);
        else if (candle instanceof FifteenMinuteCandle instance)
            return fifteenMinuteCandleRepository.save(instance);
        else if (candle instanceof ThirtyMinuteCandle instance)
            return thirtyMinuteCandleRepository.save(instance);
        else if (candle instanceof SixtyMinuteCandle instance)
            return sixtyMinuteCandleRepository.save(instance);
        else
            throw new WrongCandleException(
                    """
                    Class: CandlesServiceImpl
                    Method: Mono<?> saveCandle(Object candle)
                    """
            );
    }

    @Override
    public Mono<Boolean> isDuplicateCandle(Object candle){
        if (candle instanceof OneMinuteCandle instance)
            return oneMinuteCandleRepository.findByOpenTime(instance.getOpenTime()).hasElement();
        else if (candle instanceof ThreeMinuteCandle instance)
            return threeMinuteCandleRepository.findByOpenTime(instance.getOpenTime()).hasElement();
        else if (candle instanceof FiveMinuteCandle instance)
            return fiveMinuteCandleRepository.findByOpenTime(instance.getOpenTime()).hasElement();
        else if (candle instanceof FifteenMinuteCandle instance)
            return fifteenMinuteCandleRepository.findByOpenTime(instance.getOpenTime()).hasElement();
        else if (candle instanceof ThirtyMinuteCandle instance)
            return thirtyMinuteCandleRepository.findByOpenTime(instance.getOpenTime()).hasElement();
        else if (candle instanceof SixtyMinuteCandle instance)
            return sixtyMinuteCandleRepository.findByOpenTime(instance.getOpenTime()).hasElement();
        else
            throw new WrongCandleException(
                    """
                    Class: CandlesServiceImpl
                    Method: Mono<Boolean> isDuplicateCandle(Object candle)
                    """
            );
    }

    @Override
    public Mono<? extends Candle> findLastCandle(KindsCandles kindsCandles){
        return switch (kindsCandles){
            case ONE -> oneMinuteCandleRepository.findLastCandle();
            case THREE -> threeMinuteCandleRepository.findLastCandle();
            case FIVE -> fiveMinuteCandleRepository.findLastCandle();
            case FIFTEEN -> fifteenMinuteCandleRepository.findLastCandle();
            case THIRTY -> thirtyMinuteCandleRepository.findLastCandle();
            case SIXTY -> sixtyMinuteCandleRepository.findLastCandle();
        };
    }

    @Override
    public Flux<? extends Candle> findLastThreeCandles(KindsCandles kindsCandles){
        return switch (kindsCandles){
            case ONE -> oneMinuteCandleRepository.findLastThreeCandles();
            case THREE -> threeMinuteCandleRepository.findLastCandles();
            case FIVE -> fiveMinuteCandleRepository.findLastThreeCandles();
            case FIFTEEN -> fifteenMinuteCandleRepository.findLastThreeCandles();
            case THIRTY -> thirtyMinuteCandleRepository.findLastThreeCandles();
            case SIXTY -> sixtyMinuteCandleRepository.findLastThreeCandles();
        };
    }

    @Override
    public Flux<? extends Candle> findLastNinetyCandles(KindsCandles kindsCandles){
        return switch (kindsCandles){
            case ONE -> oneMinuteCandleRepository.findLastNinetyCandles();
            case THREE -> threeMinuteCandleRepository.findLastNinetyCandles();
            case FIVE -> fiveMinuteCandleRepository.findLastNinetyCandles();
            case FIFTEEN -> fifteenMinuteCandleRepository.findLastNinetyCandles();
            case THIRTY -> thirtyMinuteCandleRepository.findLastNinetyCandles();
            case SIXTY -> sixtyMinuteCandleRepository.findLastNinetyCandles();
        };
    }

    @Override
    public KindsCandles determineTypeCandles(List<? extends Candle> list){
        if (list.get(0) instanceof OneMinuteCandle)
            return KindsCandles.ONE;
        else if (list.get(0) instanceof ThreeMinuteCandle)
            return KindsCandles.THREE;
        else if (list.get(0) instanceof FiveMinuteCandle)
            return KindsCandles.FIVE;
        else if (list.get(0) instanceof FifteenMinuteCandle)
            return KindsCandles.FIFTEEN;
        else if (list.get(0) instanceof ThirtyMinuteCandle)
            return KindsCandles.THIRTY;
        else if (list.get(0) instanceof SixtyMinuteCandle)
            return KindsCandles.SIXTY;
        else
            throw new WrongCandleException(
                    """
                    Class: CandlesServiceImpl
                    Method: KindsCandles determineTypeCandles(List<? extends Candle> list)
                    """
            );
    }

    @Override
    public List<KindsCandles> unpackCandlesPackage(PackageCandles packageCandles){
        List<KindsCandles> kindsCandlesList = new LinkedList<>();

        if (packageCandles.getOneMinuteCandle() != null)
            kindsCandlesList.add(KindsCandles.ONE);
        if (packageCandles.getThreeMinuteCandle() != null)
            kindsCandlesList.add(KindsCandles.THREE);
        if (packageCandles.getFiveMinuteCandle() != null)
            kindsCandlesList.add(KindsCandles.FIVE);
        if (packageCandles.getFifteenMinuteCandle() != null)
            kindsCandlesList.add(KindsCandles.FIFTEEN);
        if (packageCandles.getThirtyMinuteCandle() != null)
            kindsCandlesList.add(KindsCandles.THIRTY);
        if (packageCandles.getSixtyMinuteCandle() != null)
            kindsCandlesList.add(KindsCandles.SIXTY);

        return kindsCandlesList;
    }
}
