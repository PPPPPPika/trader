package com.eduard.diploma.trader.Adapter.Account;

import com.eduard.diploma.trader.Adapter.Configuration.ClientConfiguration;
import com.eduard.diploma.trader.Adapter.CryptoCurrencies.Currencies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class WalletImpl implements Wallet {
    private final ClientConfiguration clientConfiguration;

    @Autowired
    public WalletImpl(ClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
    }

    @Override
    public Mono<String> getCurrency(Currencies currencies){
        return Mono.just(clientConfiguration.getClient().getAccount().getAssetBalance(currencies.toString()).getFree());
    }
}
