package generics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import generics.bitcoin.Bitcoin;
import generics.bitcoin.BitcoinWallet;
import generics.ethereum.Ethereum;
import generics.ethereum.EthereumWallet;
import generics.standard.StandardCurrencyHolder;
import generics.standard.dollar.Dollar;
import generics.standard.dollar.DollarOperations;
import generics.standard.dollar.DollarWallet;
import generics.standard.euro.Euro;
import generics.standard.euro.EuroWallet;

public class Wallmart {
    private Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

    private static final double BITCOIN_DOLAR_EXCHANGE_RATE = 7768;
    private static final double ETHEREUM_DOLAR_EXCHANGE_RATE = 244;
    private static final double EURO_DOLAR_EXCHANGE_RATE = 1.12;

    private static List<CurrencyHolder> walletList;

    public static void main(String[] args) {
        new Wallmart();
    }

    public Wallmart() {
        setupWalletList();

        Map<String, StandardCurrencyHolder> standardCurrencyHolderMap = new HashMap<>();
        CurrencyHolder bitcoinHolder = null;

        for (CurrencyHolder currencyHolder : walletList) {
            Currency currency = currencyHolder.getCurrency();
            log.debug(
                    "Currency of type [{}] has a value of {} dollars.",
                    currency.getClass().getSimpleName(),
                    currencyHolder.getTotalValueInDollars());
            log.debug(
                    "The holder has a currency of group type [{}] and is {}.",
                    currency.getCurrencyType(),
                    (currencyHolder.isHolderAnonymous() ? "anonymous" : "NOT anonymous"));
            if (currencyHolder instanceof StandardCurrencyHolder) {
                standardCurrencyHolderMap.put(currency.getClass().getSimpleName(), (StandardCurrencyHolder) currencyHolder);
            } else if (currency instanceof Bitcoin) {
                bitcoinHolder = currencyHolder;
            }
        }

        DollarOperations dollarOperations = new DollarOperations();
        StandardCurrencyHolder euroHolder = standardCurrencyHolderMap.get(Euro.class.getSimpleName());
        if (bitcoinHolder != null) {
            DollarWallet summedDollars = dollarOperations.sumTwoCurrencies(bitcoinHolder, euroHolder);
            log.debug(
                    "Summed dollar value of [{}] and [{}] is a value of {} dollars.",
                    new String[] {
                            bitcoinHolder.getCurrency().getClass().getSimpleName(),
                            euroHolder.getCurrency().getClass().getSimpleName(),
                            summedDollars.getTotalValueInDollars() + ""
                    });
        }
    }

    private void setupWalletList() {
        walletList = new ArrayList<>();

        Bitcoin bitcoin = new Bitcoin(BITCOIN_DOLAR_EXCHANGE_RATE);
        CurrencyHolder bitcoinWallet = new BitcoinWallet(bitcoin, 10);
        walletList.add(bitcoinWallet);

        Ethereum ethereum = new Ethereum(ETHEREUM_DOLAR_EXCHANGE_RATE);
        CurrencyHolder ethereumWallet = new EthereumWallet(ethereum, 100);
        walletList.add(ethereumWallet);

        Euro euro = new Euro(EURO_DOLAR_EXCHANGE_RATE);
        CurrencyHolder euroWallet = new EuroWallet(euro, 500);
        walletList.add(euroWallet);

        Dollar dollar = new Dollar(1);
        CurrencyHolder dolarWallet = new DollarWallet(dollar, 250);
        walletList.add(dolarWallet);
    }
}
