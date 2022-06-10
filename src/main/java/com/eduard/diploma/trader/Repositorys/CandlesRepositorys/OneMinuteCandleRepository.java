package com.eduard.diploma.trader.Repositorys.CandlesRepositorys;

import com.eduard.diploma.trader.Models.Candles.Candle;
import com.eduard.diploma.trader.Models.Candles.OneMinuteCandle;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface OneMinuteCandleRepository extends ReactiveCrudRepository<OneMinuteCandle, Long> {
    Flux<? extends Candle> saveAll(List<OneMinuteCandle> list);

    Mono<OneMinuteCandle> findByOpenTime(Long openTime);

    @Query("SELECT id, currency_pair, open_time, open_time_mos, open_price, close_time, close_time_mos, close_price, maximum_price, minimum_price " +
                "FROM public.oneminutecandle " +
                    "WHERE (SELECT MAX(id) FROM public.oneminutecandle) = id " +
                        "OR (SELECT MAX(id) - 1 FROM public.oneminutecandle) = id " +
                        "OR (SELECT MAX(id) - 2 FROM public.oneminutecandle) = id;")
    Flux<OneMinuteCandle> findLastThreeCandles();

    @Query("SELECT id, currency_pair, open_time, open_time_mos, open_price, close_time, close_time_mos, close_price, maximum_price, minimum_price " +
                "FROM public.oneminutecandle " +
                    "WHERE (SELECT MAX(id) FROM public.oneminutecandle) = id;")
    Mono<OneMinuteCandle> findLastCandle();

    @Query("SELECT id, currency_pair, open_time, open_time_mos, open_price, close_time, close_time_mos, close_price, maximum_price, minimum_price " +
                "FROM public.oneminutecandle " +
                    "WHERE id >= (SELECT MAX(id) - 90 FROM public.oneminutecandle);")
    Flux<OneMinuteCandle> findLastNinetyCandles();
}
