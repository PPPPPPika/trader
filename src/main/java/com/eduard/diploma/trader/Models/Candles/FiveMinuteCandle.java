package com.eduard.diploma.trader.Models.Candles;

import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("fiveminutecandle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiveMinuteCandle extends Candle {
    @Id
    private Long id;
    @Column("currency_pair")
    private CurrenciesPairs currencyPair;
    @Column("open_time")
    private Long openTime;
    @Column("open_time_mos")
    private String openTimeMos;
    @Column("open_price")
    private String openPrice;
    @Column("close_time")
    private Long closeTime;
    @Column("close_time_mos")
    private String closeTimeMos;
    @Column("close_price")
    private String closePrice;
    @Column("maximum_price")
    private String maxPrice;
    @Column("minimum_price")
    private String minPrice;

    @Override
    public String toString() {
        return "FiveCandles{" + "\n" +
                "id = " + id + ";\n" +
                "currencyPair = " + currencyPair + ";\n" +
                "openTime = " + openTime + ";\n" +
                "openTimeMos = " + openTimeMos + ";\n" +
                "openPrice = " + openPrice + ";\n" +
                "closeTime = " + closeTime + ";\n" +
                "closeTimeMos = " + closeTimeMos + ";\n" +
                "closePrice = " + closePrice + ";\n" +
                "maxPrice = " + maxPrice + ";\n" +
                "minPrice = " + minPrice + ";\n" +
                '}';
    }
}
