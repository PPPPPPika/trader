package com.eduard.diploma.trader.Adapter.Market;

import com.eduard.diploma.trader.Adapter.Configuration.ClientConfiguration;
import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class PriceExplorerImpl implements PriceExplorer {
    private final ClientConfiguration clientConfiguration;

    @Autowired
    public PriceExplorerImpl(ClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
    }

    @Override
    public Mono<String> getValueCurrencyPairs(CurrenciesPairs currenciesPairs) {
        return Mono.just(
                new BigDecimal(clientConfiguration.getClient().get24HrPriceStatistics(currenciesPairs.toString()).getLastPrice())
                        .setScale(2, RoundingMode.DOWN)
                        .toString()
        );
    }

    @Override
    public Mono<String> getMinPriceOrder(CurrenciesPairs currenciesPairs) {
        return Mono.just(
                new BigDecimal("11.0")
                        .divide(new BigDecimal(clientConfiguration.getClient().get24HrPriceStatistics(currenciesPairs.toString()).getLastPrice()),
                                                                                                      currenciesPairs.getScale(), RoundingMode.DOWN)
                        .toString()
        );
    }

    @Override
    public Mono<String> getAmountCurrencyForBuy(CurrenciesPairs currenciesPairs, String sumOfBuy) {
        return Mono.just(
                new BigDecimal(sumOfBuy)
                        .divide(new BigDecimal(clientConfiguration.getClient().get24HrPriceStatistics(currenciesPairs.toString()).getLastPrice()),
                                                                                                      currenciesPairs.getScale(), RoundingMode.DOWN)
                        .toString()
        );
    }
}
