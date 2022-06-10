package com.eduard.diploma.trader.Repositorys.CandlesRepositorys;

import com.eduard.diploma.trader.Models.Candles.Candle;
import com.eduard.diploma.trader.Models.Candles.SixtyMinuteCandle;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface SixtyMinuteCandleRepository extends ReactiveCrudRepository<SixtyMinuteCandle, Long> {
    Flux<? extends Candle> saveAll(List<SixtyMinuteCandle> list);

    Mono<SixtyMinuteCandle> findByOpenTime(Long openTime);

    @Query("SELECT id, currency_pair, open_time, open_time_mos, open_price, close_time, close_time_mos, close_price, maximum_price, minimum_price " +
                "from public.sixtyminutecandle " +
                    "WHERE (SELECT MAX(id) from public.sixtyminutecandle) = id " +
                        "OR (SELECT MAX(id) - 1 from public.sixtyminutecandle) = id " +
                        "OR (SELECT MAX(id) - 2 from public.sixtyminutecandle) = id;")
    Flux<SixtyMinuteCandle> findLastThreeCandles();

    @Query("SELECT id, currency_pair, open_time, open_time_mos, open_price, close_time, close_time_mos, close_price, maximum_price, minimum_price " +
                "from public.sixtyminutecandle " +
                    "WHERE (SELECT MAX(id) from public.sixtyminutecandle) = id;")
    Mono<SixtyMinuteCandle> findLastCandle();

    @Query("SELECT id, currency_pair, open_time, open_time_mos, open_price, close_time, close_time_mos, close_price, maximum_price, minimum_price " +
                "from public.sixtyminutecandle " +
                    "WHERE id >= (SELECT MAX(id) - 90 from public.sixtyminutecandle);")
    Flux<SixtyMinuteCandle> findLastNinetyCandles();

}
