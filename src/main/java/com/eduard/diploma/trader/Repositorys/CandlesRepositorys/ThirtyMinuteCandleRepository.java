package com.eduard.diploma.trader.Repositorys.CandlesRepositorys;

import com.eduard.diploma.trader.Models.Candles.Candle;
import com.eduard.diploma.trader.Models.Candles.ThirtyMinuteCandle;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface ThirtyMinuteCandleRepository extends ReactiveCrudRepository<ThirtyMinuteCandle, Long> {
    Flux<? extends Candle> saveAll(List<ThirtyMinuteCandle> list);

    Mono<ThirtyMinuteCandle> findByOpenTime(Long openTime);

    @Query("SELECT id, currency_pair, open_time, open_time_mos, open_price, close_time, close_time_mos, close_price, maximum_price, minimum_price " +
                "from public.thirtyminutecandle " +
                    "WHERE (SELECT MAX(id) from public.thirtyminutecandle) = id " +
                        "OR (SELECT MAX(id) - 1 from public.thirtyminutecandle) = id " +
                        "OR (SELECT MAX(id) - 2 from public.thirtyminutecandle) = id;")
    Flux<ThirtyMinuteCandle> findLastThreeCandles();

    @Query("SELECT id, currency_pair, open_time, open_time_mos, open_price, close_time, close_time_mos, close_price, maximum_price, minimum_price " +
                "from public.thirtyminutecandle " +
                    "WHERE (SELECT MAX(id) from public.thirtyminutecandle) = id;")
    Mono<ThirtyMinuteCandle> findLastCandle();

    @Query("SELECT id, currency_pair, open_time, open_time_mos, open_price, close_time, close_time_mos, close_price, maximum_price, minimum_price " +
                "from public.thirtyminutecandle " +
                    "WHERE id >= (SELECT MAX(id) - 90 from public.thirtyminutecandle);")
    Flux<ThirtyMinuteCandle> findLastNinetyCandles();

}
