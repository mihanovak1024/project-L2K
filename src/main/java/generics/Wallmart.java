package generics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import generics.bitcoin.Bitcoin;
import generics.bitcoin.BitcoinWallet;
import generics.ethereum.Ethereum;
import generics.ethereum.EthereumWallet;
import generics.standard.StandardCurrencyHolder;
import generics.standard.dollar.Dollar;
import generics.standard.dollar.DollarWallet;
import generics.standard.euro.Euro;
import generics.standard.euro.EuroWallet;

public class Wallmart {

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
        Bitcoin bitcoin;

        for (CurrencyHolder currencyHolder : walletList) {
            Currency currency = currencyHolder.getCurrency();
            System.out.println("Currency of type [" + currency.getClass().getSimpleName() + "] has a value of " +
                               currencyHolder.getTotalValueInDollars() + " dollars");
            System.out.println("The holder has a currency of group type [" + currency.getCurrencyType() + "] and is " +
                               (currencyHolder.isHolderAnonymous() ? "anonymous" : "not anonymous"));
            if (currencyHolder instanceof StandardCurrencyHolder) {
                standardCurrencyHolderMap.put(currency.getClass().getSimpleName(), (StandardCurrencyHolder) currencyHolder);
            } else if (currency instanceof Bitcoin) {
                bitcoin = (Bitcoin) currency;
            }
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
