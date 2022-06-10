package com.eduard.diploma.trader.Adapter.Controllers;

import com.eduard.diploma.trader.Adapter.Account.WalletImpl;
import com.eduard.diploma.trader.Adapter.Market.Action.ClientActionImpl;
import com.eduard.diploma.trader.Adapter.Market.PriceExplorerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ControllerTest1 {
    private final WalletImpl wallet;
    private final PriceExplorerImpl priceExplorer;
    /*private final PriceExplorer<String> priceExplorerBlock;
    private final ClientActionBlockImplBlock clientAction;*/
    private final ClientActionImpl clientAction;

    @Autowired
    public ControllerTest1(WalletImpl wallet,
                           PriceExplorerImpl priceExplorer,
                          /*PriceExplorerBlockImpl priceExplorerBlock,
                          ClientActionBlockImplBlock clientAction,*/
                          ClientActionImpl clientAction) {
        this.wallet = wallet;
        this.priceExplorer = priceExplorer;
       /* this.priceExplorerBlock = priceExplorerBlock;
        this.clientAction = clientAction;*/
        this.clientAction = clientAction;
    }

    /*@GetMapping("/getcurrency")
    public String test1() {
        return wallet.getCurrency(Currency.USDT).toString();
    }*/

    /*@GetMapping("/getcurrencies")
    public String test2() {
        return wallet.getCurrencies().toString();
    }*/

    /*@GetMapping("/pair")
    public Mono<String> test() {
        return priceExplorer.getValueCurrencyPairs(CurrenciesPairs.BTCBUSD);
    }
*/
    /*@GetMapping("")
    public String test() {
        clientAction.tradingAction(CurrencyPairs.ETHUSDT, OrderSide.BUY, OrderType.MARKET, "0.0043");
        return "ok";
    }*/

    /*@GetMapping("")
    public String test() {
        return priceExplorer.exchangeInformation();
    }*/

    /*@GetMapping("")
    public String test() throws ClientActionException {
        System.out.println(priceExplorer.getMinPriceOrder(CurrencyPairs.BTCBUSD));
        clientAction.createOrderTest(CurrencyPairs.BTCBUSD, OrderSide.BUY, OrderType.MARKET, "0.0003");
        return "ok";
    }*/

    /*@GetMapping("")
    public String test() {
        return priceExplorer.getValueCurrencyPairs(CurrencyPairs.BTCBUSD);
    }*/

    /*@GetMapping
    public String test() throws ClientActionException {
        clientAction.createOrderTest(CurrencyPairs.BTCBUSD, OrderSide.SELL, "0.0003");
        return "ok";
    }*/

    /*@GetMapping("/sell")
    public String test5() throws ClientActionException {
        clientAction.createOrder(CurrencyPairs.ETHBUSD, OrderSide.SELL, "");
        return "ok";
    }*/

    /*@GetMapping("/buy")
    public String test4() throws ClientActionException {
        clientAction.createOrder(CurrencyPairs.ETHBUSD, OrderSide.BUY,
                                 priceExplorer.getAmountCurrencyForBuy(CurrencyPairs.ETHBUSD, "24.16"));
        return "ok";
    }*/

    /*@GetMapping("")
    public String test() throws ClientActionException {
        priceExplorer.getInformationCurrency(CurrencyPairs.ETHUSDT).;
        return "ok";
    }*/

    /*@GetMapping("")
    public Mono<String> test() throws ClientActionException {
        return wallet.getCurrencyMono(Currency.BTC);
    }*/


    //reactor
    /*@GetMapping("/wallet")
    public Mono<String> test() throws ClientActionException {
        return priceExplorer.getValueCurrencyPairs(CurrencyPairs.BTCBUSD);
    }*/

    /*@GetMapping("/wallet")
    public Mono<String> test() throws ClientActionException {
        return priceExplorer.getAmountCurrencyForBuy(CurrencyPairs.BTCBUSD, "20");
    }*/

    /*@GetMapping("/wallet")
    public Mono<String> test() throws ClientActionException {
        return priceExplorer.getMinPriceOrder(CurrencyPairs.BTCBUSD);
    }*/

    /*@GetMapping("/wallet")
    public Mono<ResponseEntity<String>> test() throws ClientActionException {
        return clientActionNoBlock.createOrder(CurrencyPairs.ETHBUSD, OrderSide.BUY,
                                               priceExplorerBlock.getAmountCurrencyForBuy(CurrencyPairs.ETHBUSD, "25.42"));
    }*/

    /*@GetMapping("/order")
    public Mono<String> test() throws ClientActionException {
        return clientAction.createOrderTest(CurrencyPairs.ETHBUSD, OrderSide.BUY, "20.01");
    }*/

    /*@GetMapping("/order")
    public Mono<String> test() {
        return clientAction.createOrderTest(CurrenciesPairs.ETHBUSD, OrderSide.BUY, "20.01");
    }*/

    /*@GetMapping("/last")
    public Mono<Candlestick> test() {
        return priceExplorer.getMinuteValueCurrencyPairs(CurrenciesPairs.BTCBUSD);
    }*/
}
