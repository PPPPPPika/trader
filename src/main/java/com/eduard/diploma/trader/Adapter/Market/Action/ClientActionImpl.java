package com.eduard.diploma.trader.Adapter.Market.Action;

import com.binance.api.client.domain.OrderSide;
import com.binance.api.client.domain.OrderType;
import com.binance.api.client.domain.TimeInForce;
import com.binance.api.client.domain.account.NewOrder;
import com.eduard.diploma.trader.Adapter.CryptoCurrencies.CurrenciesPairs;
import com.eduard.diploma.trader.Adapter.Configuration.ClientConfiguration;
import com.eduard.diploma.trader.Adapter.Market.PriceExplorer;
import com.eduard.diploma.trader.Adapter.Market.PriceExplorerImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
public class ClientActionImpl implements ClientActions {
    private final ClientConfiguration clientConfiguration;
    private final PriceExplorer priceExplorer;

    @Autowired
    public ClientActionImpl(ClientConfiguration clientConfiguration, PriceExplorerImpl priceExplorer) {
        this.clientConfiguration = clientConfiguration;
        this.priceExplorer = priceExplorer;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    private static class Note {
        private String minPrice;
        private String valueCurrencyPair;
        private String sumOfBuy;
    }

    @Override
    public Mono<String> createOrderTest(CurrenciesPairs currencyPair, OrderSide orderSide, String quantity) {
        return Mono.zip(priceExplorer.getMinPriceOrder(currencyPair),
                        priceExplorer.getValueCurrencyPairs(currencyPair),
                        priceExplorer.getAmountCurrencyForBuy(currencyPair, quantity))
                .map(note -> new Note(note.getT1(), note.getT2(), note.getT3()))
                .filter(note -> {
                    if (new BigDecimal(quantity).compareTo(new BigDecimal(note.getMinPrice())) > 0)
                        return true;
                    else
                        try {
                            throw new ClientActionException("Low price exception", currencyPair);
                        } catch (ClientActionException clientActionException) {
                            System.out.println(clientActionException);
                            return false;
                        }
                })
                .map(note -> {
                    clientConfiguration.getClient().newOrderTest(new NewOrder(currencyPair.toString(), orderSide,
                                                                              OrderType.LIMIT, TimeInForce.GTC,
                                                                              note.getSumOfBuy(), note.getValueCurrencyPair()));
                    return "Order created";
                });
    }

    @Override
    public Mono<String> createOrder(CurrenciesPairs currencyPair, OrderSide orderSide, String quantity) {
        return Mono.zip(priceExplorer.getMinPriceOrder(currencyPair),
                        priceExplorer.getValueCurrencyPairs(currencyPair),
                        priceExplorer.getAmountCurrencyForBuy(currencyPair, quantity))
                .map(note -> new Note(note.getT1(), note.getT2(), note.getT3()))
                .filter(note -> {
                    if (new BigDecimal(quantity).compareTo(new BigDecimal(note.getMinPrice())) > 0)
                        return true;
                    else
                        try {
                            throw new ClientActionException("Low price exception", currencyPair);
                        } catch (ClientActionException clientActionException) {
                            System.out.println(clientActionException);
                            return false;
                        }
                })
                .map(note -> {
                    clientConfiguration.getClient().newOrder(new NewOrder(currencyPair.toString(), orderSide,
                                                                              OrderType.LIMIT, TimeInForce.GTC,
                                                                              note.getSumOfBuy(), note.getValueCurrencyPair()));
                    return "Order created";
                })
                .onErrorReturn("Order do not created");
    }
}
