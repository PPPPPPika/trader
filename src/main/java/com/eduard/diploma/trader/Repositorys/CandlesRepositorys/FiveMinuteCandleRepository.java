package com.eduard.diploma.trader.Repositorys.CandlesRepositorys;

import com.eduard.diploma.trader.Models.Candles.Candle;
import com.eduard.diploma.trader.Models.Candles.FiveMinuteCandle;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface FiveMinuteCandleRepository extends ReactiveCrudRepository<FiveMinuteCandle, Long> {
    Flux<? extends Candle> saveAll(List<FiveMinuteCandle> list);

    Mono<FiveMinuteCandle> findByOpenTime(Long openTime);

    @Query("SELECT id, currency_pair, open_time, open_time_mos, open_price, close_time, close_time_mos, close_price, maximum_price, minimum_price " +
                "FROM public.fiveminutecandle " +
                    "WHERE (SELECT MAX(id) FROM public.fiveminutecandle) = id " +
                        "OR (SELECT MAX(id) - 1 FROM public.fiveminutecandle) = id " +
                        "OR (SELECT MAX(id) - 2 FROM public.fiveminutecandle) = id;")
    Flux<FiveMinuteCandle> findLastThreeCandles();

    @Query("SELECT id, currency_pair, open_time, open_time_mos, open_price, close_time, close_time_mos, close_price, maximum_price, minimum_price " +
                "from public.fiveminutecandle " +
                    "WHERE (SELECT MAX(id) from public.fiveminutecandle) = id;")
    Mono<FiveMinuteCandle> findLastCandle();

    @Query("SELECT id, currency_pair, open_time, open_time_mos, open_price, close_time, close_time_mos, close_price, maximum_price, minimum_price " +
                "from public.fiveminutecandle " +
                    "WHERE id >= (SELECT MAX(id) - 90 from public.fiveminutecandle);")
    Flux<FiveMinuteCandle> findLastNinetyCandles();
}
